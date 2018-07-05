pragma solidity ^0.4.24;


/**
 * @title Ownable
 * @dev The Ownable contract has an owner address, and provides basic authorization control
 * functions, this simplifies the implementation of "user permissions".
 */
contract Ownable {

  address public owner;

  event OwnershipTransferred(
    address indexed previousOwner,
    address indexed newOwner
  );

  /**
   * @dev The Ownable constructor sets the original `owner` of the contract to the sender
   * account.
   */
  constructor() public {
    owner = msg.sender;
  }

  /**
   * @dev Throws if called by any account other than the owner.
   */
  modifier onlyOwner() {
    require(msg.sender == owner);
    _;
  }

  /**
   * @dev Allows the current owner to transfer control of the contract to a newOwner.
   * @param _newOwner The address to transfer ownership to.
   */
  function transferOwnership(address _newOwner) public onlyOwner {
    _transferOwnership(_newOwner);
  }

  /**
   * @dev Transfers control of the contract to a newOwner.
   * @param _newOwner The address to transfer ownership to.
   */
  function _transferOwnership(address _newOwner) internal {
    require(_newOwner != address(0));
    emit OwnershipTransferred(owner, _newOwner);
    owner = _newOwner;
  }
}

/**
 * @title Storage contract
 * @dev The Storage contract has all project functionality as store and get data
 */
contract Storage is Ownable {

  event SubOwnerAdded(address subOwner);
  event SubOwnerRemoved(address subOwner);
  event DataStored(uint indexed ix_id, string id_message, string id_from, string id_to, string sha);

  mapping (address => bool) public subOwners;

  /**
   * @dev Throws if called by any account other than the owner or subOwner.
   */
  modifier onlySubOwner() {
    require (msg.sender == owner || subOwners[msg.sender]);
    _;
  }

  struct Store {
    string id_message;
    string id_from;
    string id_to;
    string sha;
  }

  Store[] public store;
  mapping (string => uint) shaStore;

  /**
   * @dev Allows the current owner to add new sub owner.
   * @param _address The new sub owner address.
   */
  function addSubOwner (address _address) onlyOwner public  {
    require (!subOwners[_address]);
    subOwners[_address] = true;

    emit SubOwnerAdded(_address);
  }

  /**
   * @dev Allows the current owner to remove existing sub owner.
   * @param _address The sub owner address.
   */
  function removeSubOwner (address _address) onlyOwner public  {
    require (subOwners[_address]);
    subOwners[_address] = false;

    emit SubOwnerRemoved(_address);
  }

  /**
   * @dev Allows the current owner to remove existing sub owner.
   * @param _id_message input param.
   * @param _id_from input param.
   * @param _id_to The input param.
   * @param _sha The input param.
   */
  function storeData (
   string _id_message,
   string _id_from,
   string _id_to,
   string _sha) onlySubOwner public  {
    require (shaStore[_sha] == 0);

    store.push(Store(_id_message, _id_from, _id_to, _sha));
    shaStore[_sha] = store.length;

    emit DataStored(store.length, _id_message, _id_from, _id_to, _sha);
  }

  /**
   * @dev Allows the current owner to remove existing sub owner.
   * @param _tx_id Transaction id.
   */
  function findById (uint _tx_id) public view returns (string, string, string, string) {
    Store memory buffer = store[_tx_id-1];

    return (buffer.id_message, buffer.id_from, buffer.id_to, buffer.sha);
  }

  /**
   * @dev Allows the current owner to remove existing sub owner.
   * @param _sha input param.
   */
  function findBySha (string _sha) public view returns (string, string, string, uint) {
    uint id = shaStore[_sha];
    Store memory buffer = store[id-1];

    return (buffer.id_message, buffer.id_from, buffer.id_to, id);
  }
}