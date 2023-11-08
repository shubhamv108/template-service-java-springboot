set -xeau

echo "Creating .git/hooks defensively"
mkdir -p .git/hooks
cp scripts/pre-push .git/hooks
chmod +x .git/hooks/pre-push

echo "***** Running tests as pre-push hook *****"
make tests