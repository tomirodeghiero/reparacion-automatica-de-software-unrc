# Virtual environments

This is optional, but if you don't want to install packages globally, you can use virtual environments so packages are only installed locally for each project.

## Using venv

We start by executing `python<v> -m venv <env_name>` where `<v>` is the version of **Python** we want to use, and `<env_name>` is the name of the environment.

For example:
```Bash
python3.10 -m venv .env
```

You may need to install `python3.10-venv` (or equivalent, depending on your version) using

```Bash
sudo apt install python3.10-venv
```

Then you can activate the environment using

```Bash
source .env/bin/activate
```

With the environment activated, you can install dependencies locally by executing 

```Bash
pip install -r requirements.txt
```

Finally, to deactivate the environment, you can use `deactivate`

## Running tests

To run a test with pytest, just execute `pytest [<test file to run>] [--cov=<module>] [--cov-branch] [--cov-report=html]`

Where `[<test file to run>]` is an optional argument if you want to run a specific file, if you want a specific test inside a test file, you can add `::test_function_name`.

With `[--cov=<module>]` you can optionally enable coverage metrics for a particular module.

With `[--cov-branch]` you can optionally enable branch coverage.

With `[--cov-report=html]` you can optionally enable a report in html format.
