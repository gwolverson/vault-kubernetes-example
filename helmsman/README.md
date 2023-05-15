# helmsman

## Prerequsites
- helm
- helmsman

## Description
Desired state files and values for Helm chart installation

This process uses [Helmsman](https://github.com/Praqma/helmsman) which allows you to automate the deployment/management of your Helm charts from version controlled code.

```
.
├── README.md                       # this file
├── dev.yaml                        # desired state file for non-prod application cluster (application tenant)
└── values
    ├── service
    │   └── values-dev.yaml    # helm values for the dev service chart installation
    ├── vault
    │   └── values-dev.yaml    # helm values for the dev vault chart installation
```

## Usage

to test a desired state file 
```
# helmsman -f <desired_state> --check-for-chart-updates --no-ns --dry-run
# options;
# --check-for-chart-updates displays information on whether this chart can be updated
# --no-ns prevents helmsman from attempting to create a namespace if it doesn't exist
# --dry-run display the manifests rather than install them (omit this to install)

helmsman -f test.yaml --check-for-chart-updates --no-ns --dry-run
```
to install a desred state file
```
helmsman -f test.yaml --check-for-chart-updates --no-ns
```