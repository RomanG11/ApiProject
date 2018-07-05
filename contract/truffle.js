const PrivateKeyProvider = require("truffle-privatekey-provider");

const privateKey = "8c9da8046bb0d7ae1687dac46b3515224d7c0c398c7a6769167cc9a331982430";

const provider =  new PrivateKeyProvider(privateKey, "https://rinkeby.infura.io/jV2i3C9g4hfww8EoSpHs");

module.exports = {
  networks: {
    development: {
      host: "localhost",
      port: 8545,
      network_id: "*"
    },
    	rinkeby: {
      // provider: function() {
        // return new HDWalletProvider(privateKey, "https://rinkeby.infura.io/jV2i3C9g4hfww8EoSpHs")
      // },
      provider: provider,
      network_id: 4,
      gas: 4000000,
      gasPrice: 21000000000
    }
  }
};