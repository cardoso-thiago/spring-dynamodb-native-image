aws dynamodb create-table \
    --table-name artist_details \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --region sa-east-1 \
    --output json \
    --endpoint-url http://localhost:4566