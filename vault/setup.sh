#!/bin/bash

vault auth enable kubernetes 

vault write auth/kubernetes/config kubernetes_host=https://$KUBERNETES_SERVICE_HOST:$KUBERNETES_SERVICE_PORT 

vault write auth/kubernetes/role/demo bound_service_account_names='*' bound_service_account_namespaces=dev policies=spring-boot-demo  

vault policy write spring-boot-demo /tmp/policy.json 

vault secrets enable kv 

vault kv put kv/spring-boot-demo/dev admin=password  