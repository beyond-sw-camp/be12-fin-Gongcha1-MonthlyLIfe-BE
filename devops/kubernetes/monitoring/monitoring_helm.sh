#!/bin/bash

# Helm repo 추가 및 업데이트
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

# 네임스페이스가 없으면 생성
kubectl get namespace monitoring >/dev/null 2>&1 || kubectl create namespace monitoring

# Prometheus 설치
helm install prometheus prometheus-community/prometheus --namespace monitoring

# Grafana 설치
helm install grafana grafana/grafana --namespace monitoring