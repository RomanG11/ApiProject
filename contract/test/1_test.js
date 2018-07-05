var Storage = artifacts.require("Storage.sol");

expect = require("chai").expect;

contract("Storage contract", function(accounts){

  describe("Initial tests", function(){
    it("catch contract instance", function(){
      return Storage.deployed().then(function(instance){
        StorageInstance = instance;
      });
    });
    it("check owner's address", function(){
      return StorageInstance.owner.call().then(function(res){
        expect(res.toString()).to.be.equal(accounts[0]);
      });
    });
  });

  describe ("Storage contract tests", function () {
  	it ("check new subOwner in mapping", function(){
    	return StorageInstance.subOwners.call(accounts[1]).then(function(res){
    		expect(res).to.be.equal(false);
    	})
    })
    it ("Try to add new subOwner by Owner", async function(){
       try {
        await StorageInstance.addSubOwner(accounts[1]);
        assert.ok(true, "Ok")
      } catch(error){
        assert.ok(false, "Something went wrong");
      }
    })

    it ("check new subOwner in mapping", function(){
    	return StorageInstance.subOwners.call(accounts[1]).then(function(res){
    		expect(res).to.be.equal(true)
    	})
    })
    it ("Try to add new subOwner by subOwner", async function(){
      try {
        await StorageInstance.addSubOwner(accounts[2],{from: accounts[1]})
        assert.ok(false, "Ok")
      } catch(error){
        assert.ok(true, "Something went wrong");
      }
    })
    it ("try to store data by not subOwner", async function(){
      try {
        await StorageInstance.storeData("0","0","0","sha0",{from: accounts[4]});
        assert.ok(false, "Something went wrong")
      } catch(error){
        assert.ok(true, "Ok");
      }
    })
    it ("try to store data by subOwner", async function(){
      try {
        await StorageInstance.storeData("1","1","1","sha1",{from: accounts[1]});
        assert.ok(true, "Ok")
      } catch(error){
        assert.ok(false, "Something went wrong");
      }
    })
    it ("try to store data by Owner", async function(){
      try {
        await StorageInstance.storeData("2","2","2","sha2",{from: accounts[0]});
        assert.ok(true, "Ok")
      } catch(error){
        assert.ok(false, "Something went wrong");
      }
    })
    it ("try to store data with the same sha", async function(){
      try {
        await StorageInstance.storeData("3","3","3","sha2",{from: accounts[0]});
        assert.ok(false, "Something went wrong")
      } catch(error){
        assert.ok(true, "Ok");
      }
    })
    it ("get data by id", function(){
    	return StorageInstance.findById(1).then(function(res){
        expect(res.toString()).to.be.equal('1,1,1,sha1');
    	})
    })
    it ("get data by sha", function(){
    	return StorageInstance.findBySha("sha2").then(function(res){
        expect(res.toString()).to.be.equal('2,2,2,2');
    	})
    })
  })
  describe ("check Ownable contract", function(){
    it ("check TransferOwnerShip function", async function(){
      try{
        await StorageInstance.transferOwnership(accounts[5],{from: accounts[0]});
        assert.ok(true, "Ok");
      }catch(error){
        assert.ok(false, "Something went wrong")
      }
    })
    it ("try to do it again", async function(){
      try{
        await StorageInstance.transferOwnership(accounts[5],{from: accounts[0]});
        assert.ok(false, "Something went wrong");
      }catch(error){
        assert.ok(true, "Ok")
      }
    })
    it ("check owner now", function(){
      return StorageInstance.owner.call().then(function(res){
        expect(res.toString()).to.be.equal(accounts[5])
      })
    })
  })
})
