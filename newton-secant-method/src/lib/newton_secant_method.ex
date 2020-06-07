defmodule NewtonSecantMethod do
  use Application

  def start(_type, _args) do
    # Чтение аргументов
    args = parse_args(args_default(), System.argv())
    source = Map.get(args, :source)

    # Чтение конфигурации из YAML
    {_, config} = YamlElixir.read_from_file(source)

    # Границы отрезка
    a = config |> Map.get("domain") |> Map.get("a")
    b = config |> Map.get("domain") |> Map.get("b")

    # Шаг отделения корней
    step = config |> Map.get("step")

    # "Близость" уточнения корней
    eps = config |> Map.get("eps")

    # Функция и ее производные
    fun = config |> Map.get("fun")
    dfun = config |> Map.get("derivatives") |> Map.get("first")
    ddfun = config |> Map.get("derivatives") |> Map.get("second")

    # Обертки, исполняющие код, указанный в YAML
    f = fn x -> Code.eval_string(fun, x: x) |> elem(0) end
    df = fn x -> Code.eval_string(dfun, x: x) |> elem(0) end
    ddf = fn x -> Code.eval_string(ddfun, x: x) |> elem(0) end

    roots_intervals =
      separate_roots(a, b, step, f, df)
      |> Enum.chunk_every(2, 2, :discard)

    roots =
      roots_intervals
      |> Enum.map(fn [x, y] -> find_root(x, y, f, df, ddf, eps) end)
      |> Enum.uniq()

    title = "Roots of #{fun} = 0"
    header = ["x", "Residual"]

    roots
    |> Enum.map(fn x -> [x, f.(x)] end)
    |> TableRex.quick_render!(header, title)
    |> IO.puts()

    Task.start(fn -> :timer.sleep(1000) end)
  end

  def find_root(xl, xr, f, df, ddf, eps) do
    xln =
      cond do
        f.(xl) * ddf.(xl) < 0 ->
          xl - f.(xl) * (xl - xr) / (f.(xl) - f.(xr))

        true ->
          xl - f.(xl) / df.(xl)
      end

    xrn =
      cond do
        f.(xr) * ddf.(xr) < 0 ->
          xr - f.(xr) * (xr - xl) / (f.(xr) - f.(xl))

        true ->
          xr - f.(xr) / df.(xl)
      end

    if abs(xln - xrn) >= eps do
      find_root(xln, xrn, f, df, ddf, eps)
    else
      (xln + xrn) / 2
    end
  end

  def separate_roots(a, b, step, f, df) do
    if step < 1 / 128 do
      nil
    else
      seq(a, b, step)
      |> Enum.chunk_every(2, 1, :discard)
      |> Enum.map(fn [x, y] ->
        {fx, fy} = {f.(x), f.(y)}

        cond do
          # Знак функции на концах разный => корни есть
          sign(fx) != sign(fy) ->
            # на [x,y] монотонная => корень (возможно кратный)
            if monotonic?(x, y, step / 4, df) do
              [x, y]
            else
              separate_roots(x, y, step / 2, f, df)
            end

          # Знак функции одинаковый => возможно корней нет
          sign(fx) == sign(fy) ->
            # Монотонная => корней на отрезке нет
            if monotonic?(x, y, step / 4, df, true) do
              nil
            else
              separate_roots(x, y, step / 2, f, df)
            end
        end
      end)
      |> Enum.filter(fn x -> not is_nil(x) end)
      |> List.flatten()
    end
  end

  def seq(a, b, step) do
    Stream.iterate(a, &(&1 + step))
    |> Enum.take_while(fn x -> x <= b end)
  end

  def monotonic?(a, b, step, df, strict \\ false) do
    uniques_length =
      seq(a, b, step)
      |> Enum.map(fn x -> sign(df.(x)) end)
      |> Enum.uniq()
      |> Enum.count()

    if strict do
      uniques_length == 1
    else
      uniques_length <= 2
    end
  end

  def sign(x) do
    cond do
      x > 0 -> 1
      x < 0 -> -1
      true -> 0
    end
  end

  def args_default do
    %{:source => "assignment.yml", :destination => "result.yml"}
  end

  def parse_args(map, argv) do
    case argv do
      [] ->
        map

      ["source" | [value | tail]] ->
        parse_args(Map.put(map, :source, value), tail)

      ["dest" | [value | tail]] ->
        parse_args(Map.put(map, :destination, value), tail)

      [option | _] ->
        IO.puts("Unknown option #{option}")
        map
    end
  end
end
