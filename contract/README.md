# Smart contract with test into Truffle project.

To check test's install global modules first:
1. Truffle
2. ganache-cli

Run ganache-cli in first console.
Then write "truffle test" in second console.

Tests correctly working only in ganache-cli.

To deploy contract change config.json file, then write:
truffle migrate --network production

To avoid unnecessary transactions (Migration contract) use remix.ethereum.org or any other deploying method. Bytecode you can find in "build/contracts/storage.json"
