const config = require('./config.json');

const PrivateKeyProvider = require("truffle-privatekey-provider");

// const privateKey = "8c9da8046bb0d7ae1687dac46b3515224d7c0c398c7a6769167cc9a331982430";

const provider =  new PrivateKeyProvider(config.privateKey, config.host);

module.exports = {
  networks: {
    development: {
      host: "localhost",
      port: 8545,
      network_id: "*"
    },
    	production: {
      provider: provider,
      network_id: 1,
      gas: 4000000,
      gasPrice: config.gasPrice
    }
  }
};