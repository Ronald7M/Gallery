cd ../ProjectAngular
docker rmi frontend
docker buildx build --platform linux/amd64 -t frontend --load .
docker save -o $PSScriptRoot/result_sv/frontend.tar frontend
scp $PSScriptRoot/result_sv/frontend.tar root@176.223.125.188:/root/tar

ssh root@176.223.125.188 '/root/script/frontend.sh'