export AWS_CLI_NO_VERIFY_SSL=1

aws --endpoint-url=http://localhost:4566 cloudformation describe-stack-events \
--stack-name healthcare-system-management \
--query "StackEvents[?ResourceStatus=='CREATE_FAILED' || ResourceStatus=='UPDATE_FAILED'].[Timestamp,LogicalResourceId,ResourceStatusReason]" \
--output table