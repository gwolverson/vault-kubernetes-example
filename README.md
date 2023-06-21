# Vault Kubernetes Example

This project showcases a demo setup of a local (in cluster) HashiCorp Vault, integrated with Kubernetes and Spring Boot.

## Spring Boot App

The sample spring boot application contains some secure actuator routes which require basic auth to access (the credentials for which are stored in Vault). 

To build the docker image run:

`docker build -t service:1.0.0 .`

## Service Helm Chart

This project contains a set of Helm templates for the Kubernetes manifests needed by the project;

- Deployment
- Config Map
- Network Policy

## Helmsman

This project contains a Helmsman project to install the required apps into the cluster.

To install the `dev` desired state file, run:

`helmsman -f dev.yml --apply`

## Vault

This project contains the relevant vault bootstrap scripts to enable and configure the Kubernetes Auth method on our in-memory vault instance. The scripts do the following;

- Enable the kubernetes auth method
- Configure the kubernetes auth method to use the [local service account token reviewer](https://developer.hashicorp.com/vault/docs/auth/kubernetes#use-local-service-account-token-as-the-reviewer-jwt) 
- Creates a `demo` role with an appropriate policy to read specific secrets
- Creates a `kv` secret engine to hold our admin access secret for the spring boot app to use

## Running

To run this example setup you'll need the following pre-requisites installed:

- [Helmsman](https://github.com/Praqma/helmsman#install)
- Kubernetes (I use [Rancher Desktop](https://docs.rancherdesktop.io/getting-started/installation/))

To run, from the root of this project, run:

`sh ./run.sh` 

This will take about 1-2 minutes and then everything should be setup. You can then `port-forward` to the app and run some test curl commands to verify the secrets are working:

`kubectl port-forward deploy/spring-boot-vault-demo 8080:8080 -n dev`

Curl without passing auth will result in a 401

`curl http://localhost:8080/actuator/env -v` 

Curl with auth will result in a 200 (and giving a JSON response)

`curl http://localhost:8080/actuator/env --user ADMIN:password -v` 