cd ../BackendShop
docker rmi backend
docker buildx build --platform linux/amd64 -t backend --load .
docker save -o $PSScriptRoot/result_sv/backend.tar backend
scp $PSScriptRoot/result_sv/backend.tar root@176.223.125.188:/root/tar

ssh root@176.223.125.188 '/root/script/backend.sh'
