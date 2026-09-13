# Running mutmut

Everything is already configured, just create a *Python* environment, install all required packages, run *mutmut* and analyze results.

## Starting

  1. Create a *Python* environment `python3.10 -m venv .mutmut` (you can choose a different environment name)
  2. Activate the environment `source .mutmut/bin/activate`
  3. Install required packages `pip install -r requirements.txt`
  
## Running mutmut

Execute `mutmut run`

## Analyzing results

* Using `mutmut browse` you will be able to see all surviving mutants and see the code of it.
* Using `mutmut export-cicd-stats` will generate a file in `mutants/mutmut-cicd-stats.json` with general information.
* Using `mutmut results` will show surviving mutants.
* Using `mutmut show <mut-id>` will show more information for mutant with id `mut-id`
* You can visualize specific information for each mutant by running
	`for i in {1..<total mutants>}; do mutmut show <base mutant name>__mutmut_$i; done`
	
## Cleaning up

 1. Delete all mutant related information `rm -rf mutants/`
 2. Deactivate *Python* environment `deactivate`
 3. Remove all installed packages (only if you don´t plan on running mutation soon) `rm -rf .mutmut`
