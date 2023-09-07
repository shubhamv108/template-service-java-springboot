set -xeau

echo "Creating git pre commit hook"

mkdir -p .git/hooks
cp scripts/pre-commit .git/hooks/
chmod +x .git/hooks/pre-commit

echo "***** Running fix format on the code *****"
make format

echo "***** Adding files to git staging area *****"

CHANGED_FILES=$(git diff --cached --name-only --diff-filter=ACM -- '*.php')

if \[ -n "$CHANGED_FILES" \]; then
    vendor/bin/php-cs-fixer fix $CHANGED_FILES;
    git add $CHANGED_FILES;
fi