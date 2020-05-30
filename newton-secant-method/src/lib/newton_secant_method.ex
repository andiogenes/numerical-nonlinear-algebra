defmodule NewtonSecantMethod do
  use Application

  def start(_type, _args) do
    IO.puts "hi"
    Task.start(fn -> :timer.sleep(1000) end)
  end
end
