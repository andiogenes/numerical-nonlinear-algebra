defmodule NewtonSecantMethod do
  use Application

  def start(_type, _args) do
    args = parse_args(args_default(), System.argv)
    IO.puts Map.get(args, :source)

    Task.start(fn -> :timer.sleep(1000) end)
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
        IO.puts "Unknown option #{option}"
        map
    end
  end
end
