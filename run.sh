#!/bin/bash

# Installed helmsman desired state

helmsman --apply -f helmsman/dev.yaml 

echo "Waiting 20 seconds for Vault to start"

sleep 20

# Vault Configuration

echo "Configuring Vault Kube Auth Method"

kubectl cp ./vault/setup.sh vault-0:/tmp -n dev

kubectl cp ./vault/policy.json vault-0:/tmp/policy.json -n dev

kubectl exec -it vault-0 sh /tmp/setup.sh -n dev

kubectl rollout restart deploy spring-boot-vault-demo -n dev

# Example Curl commands to test spring boot app

# Without Auth: curl http://localhost:8080/actuator/env -v 

# With Auth: curl http://localhost:8080/actuator/env --user ADMIN:password -v 