package com.example.api.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p><strong>Do not modify!</strong>
 */
public class Storage extends Contract {
    private static final String BINARY = "608060405260008054600160a060020a031916331790556114cc806100256000396000f3006080604052600436106100985763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630a6eccd0811461009d578063324e0ce5146100d257806335624df6146101e75780636057361d1461038b5780638da5cb5b1461054d5780639a64a2761461057e578063e530d14c14610596578063f2fde38b146105b7578063f9d86331146105d8575b600080fd5b3480156100a957600080fd5b506100be600160a060020a03600435166105f9565b604080519115158252519081900360200190f35b3480156100de57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101e594369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375094975061060e9650505050505050565b005b3480156101f357600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526102409436949293602493928401919081908401838280828437509497506109b99650505050505050565b60405180806020018060200180602001858152602001848103845288818151815260200191508051906020019080838360005b8381101561028b578181015183820152602001610273565b50505050905090810190601f1680156102b85780820380516001836020036101000a031916815260200191505b50848103835287518152875160209182019189019080838360005b838110156102eb5781810151838201526020016102d3565b50505050905090810190601f1680156103185780820380516001836020036101000a031916815260200191505b50848103825286518152865160209182019188019080838360005b8381101561034b578181015183820152602001610333565b50505050905090810190601f1680156103785780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390f35b34801561039757600080fd5b506103a3600435610cd5565b6040518080602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b838110156103ec5781810151838201526020016103d4565b50505050905090810190601f1680156104195780820380516001836020036101000a031916815260200191505b5085810384528851815288516020918201918a019080838360005b8381101561044c578181015183820152602001610434565b50505050905090810190601f1680156104795780820380516001836020036101000a031916815260200191505b50858103835287518152875160209182019189019080838360005b838110156104ac578181015183820152602001610494565b50505050905090810190601f1680156104d95780820380516001836020036101000a031916815260200191505b50858103825286518152865160209182019188019080838360005b8381101561050c5781810151838201526020016104f4565b50505050905090810190601f1680156105395780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b34801561055957600080fd5b50610562610f46565b60408051600160a060020a039092168252519081900360200190f35b34801561058a57600080fd5b506103a3600435610f55565b3480156105a257600080fd5b506101e5600160a060020a036004351661120d565b3480156105c357600080fd5b506101e5600160a060020a03600435166112a6565b3480156105e457600080fd5b506101e5600160a060020a03600435166112c9565b60016020526000908152604090205460ff1681565b600054600160a060020a031633148061063657503360009081526001602052604090205460ff165b151561064157600080fd5b6003816040518082805190602001908083835b602083106106735780518252601f199092019160209182019101610654565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220541591506106ad905057600080fd5b60408051608081018252858152602080820186905291810184905260608101839052600280546001810180835560009290925282518051929460049092027f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace019261071b92849201906113dc565b50602082810151805161073492600185019201906113dc565b50604082015180516107509160028401916020909101906113dc565b506060820151805161076c9160038401916020909101906113dc565b505050506002805490506003826040518082805190602001908083835b602083106107a85780518252601f199092019160209182019101610789565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055506002805490507fd10a297775cb4adf1bb5e42bf9571be33f111d04b56bd11aa078778c4c80ba44858585856040518080602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b8381101561085357818101518382015260200161083b565b50505050905090810190601f1680156108805780820380516001836020036101000a031916815260200191505b5085810384528851815288516020918201918a019080838360005b838110156108b357818101518382015260200161089b565b50505050905090810190601f1680156108e05780820380516001836020036101000a031916815260200191505b50858103835287518152875160209182019189019080838360005b838110156109135781810151838201526020016108fb565b50505050905090810190601f1680156109405780820380516001836020036101000a031916815260200191505b50858103825286518152865160209182019188019080838360005b8381101561097357818101518382015260200161095b565b50505050905090810190601f1680156109a05780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390a250505050565b60608060606000806109c961145a565b6003876040518082805190602001908083835b602083106109fb5780518252601f1990920191602091820191016109dc565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549150600260018303815481101515610a4357fe5b600091825260209182902060408051600493909302909101805460026001821615610100026000190190911604601f8101859004909402830160a09081019092526080830184815292939092849290918491840182828015610ae65780601f10610abb57610100808354040283529160200191610ae6565b820191906000526020600020905b815481529060010190602001808311610ac957829003601f168201915b50505050508152602001600182018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610b885780601f10610b5d57610100808354040283529160200191610b88565b820191906000526020600020905b815481529060010190602001808311610b6b57829003601f168201915b5050509183525050600282810180546040805160206001841615610100026000190190931694909404601f81018390048302850183019091528084529381019390830182828015610c1a5780601f10610bef57610100808354040283529160200191610c1a565b820191906000526020600020905b815481529060010190602001808311610bfd57829003601f168201915b505050918352505060038201805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152938201939291830182828015610cae5780601f10610c8357610100808354040283529160200191610cae565b820191906000526020600020905b815481529060010190602001808311610c9157829003601f168201915b50505091909252505081516020830151604090930151909a92995097509295509350505050565b6002805482908110610ce357fe5b60009182526020918290206004919091020180546040805160026001841615610100026000190190931692909204601f810185900485028301850190915280825291935091839190830182828015610d7c5780601f10610d5157610100808354040283529160200191610d7c565b820191906000526020600020905b815481529060010190602001808311610d5f57829003601f168201915b505050505090806001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610e1a5780601f10610def57610100808354040283529160200191610e1a565b820191906000526020600020905b815481529060010190602001808311610dfd57829003601f168201915b50505060028085018054604080516020601f6000196101006001871615020190941695909504928301859004850281018501909152818152959695945090925090830182828015610eac5780601f10610e8157610100808354040283529160200191610eac565b820191906000526020600020905b815481529060010190602001808311610e8f57829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015610f3c5780601f10610f1157610100808354040283529160200191610f3c565b820191906000526020600020905b815481529060010190602001808311610f1f57829003601f168201915b5050505050905084565b600054600160a060020a031681565b606080606080610f6361145a565b600280546000198801908110610f7557fe5b600091825260209182902060408051600493909302909101805460026001821615610100026000190190911604601f8101859004909402830160a090810190925260808301848152929390928492909184918401828280156110185780601f10610fed57610100808354040283529160200191611018565b820191906000526020600020905b815481529060010190602001808311610ffb57829003601f168201915b50505050508152602001600182018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156110ba5780601f1061108f576101008083540402835291602001916110ba565b820191906000526020600020905b81548152906001019060200180831161109d57829003601f168201915b5050509183525050600282810180546040805160206001841615610100026000190190931694909404601f8101839004830285018301909152808452938101939083018282801561114c5780601f106111215761010080835404028352916020019161114c565b820191906000526020600020905b81548152906001019060200180831161112f57829003601f168201915b505050918352505060038201805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529382019392918301828280156111e05780601f106111b5576101008083540402835291602001916111e0565b820191906000526020600020905b8154815290600101906020018083116111c357829003601f168201915b505050919092525050815160208301516040840151606090940151919a9099509297509550909350505050565b600054600160a060020a0316331461122457600080fd5b600160a060020a03811660009081526001602052604090205460ff161561124a57600080fd5b600160a060020a038116600081815260016020818152604092839020805460ff1916909217909155815192835290517fe3772d952738044f4ce6bd3d37f413339d207460a0fb78280c48d2704ab893c19281900390910190a150565b600054600160a060020a031633146112bd57600080fd5b6112c68161135f565b50565b600054600160a060020a031633146112e057600080fd5b600160a060020a03811660009081526001602052604090205460ff16151561130757600080fd5b600160a060020a038116600081815260016020908152604091829020805460ff19169055815192835290517f02e64b25b4100bd9309507ffa9a49d4f50ffec109e41334dc504a4dec6525fc19281900390910190a150565b600160a060020a038116151561137457600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061141d57805160ff191683800117855561144a565b8280016001018555821561144a579182015b8281111561144a57825182559160200191906001019061142f565b50611456929150611483565b5090565b608060405190810160405280606081526020016060815260200160608152602001606081525090565b61149d91905b808211156114565760008155600101611489565b905600a165627a7a7230582031d9484e675f1ce2578dfa7279defa6e23a015536e5c9da39c91bad56e61f96b0029";

    public static final String FUNC_SUBOWNERS = "subOwners";

    public static final String FUNC_STOREDATA = "storeData";

    public static final String FUNC_FINDBYSHA = "findBySha";

    public static final String FUNC_STORE = "store";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_FINDBYID = "findById";

    public static final String FUNC_ADDSUBOWNER = "addSubOwner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_REMOVESUBOWNER = "removeSubOwner";

    public static final Event SUBOWNERADDED_EVENT = new Event("SubOwnerAdded", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event SUBOWNERREMOVED_EVENT = new Event("SubOwnerRemoved", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event DATASTORED_EVENT = new Event("DataStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}),
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
            Arrays.<TypeReference<?>>asList());
    ;

    public Storage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public Storage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Boolean> subOwners(String param0) {
        final Function function = new Function(FUNC_SUBOWNERS, 
                Arrays.<Type>asList(new Address(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> storeData(String _id_message, String _id_from, String _id_to, String _sha) {
        final Function function = new Function(
                FUNC_STOREDATA, 
                Arrays.<Type>asList(new Utf8String(_id_message),
                new Utf8String(_id_from),
                new Utf8String(_id_to),
                new Utf8String(_sha)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple4<String, String, String, BigInteger>> findBySha(String _sha) {
        final Function function = new Function(FUNC_FINDBYSHA, 
                Arrays.<Type>asList(new Utf8String(_sha)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<String, String, String, BigInteger>>(
                new Callable<Tuple4<String, String, String, BigInteger>>() {
                    @Override
                    public Tuple4<String, String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);

                        if (results.size() == 0){
                            return new Tuple4<String, String, String, BigInteger>(
                                    "0",
                                    "0",
                                    "0",
                                    new BigInteger("-1"));

                        }

                        return new Tuple4<String, String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<Tuple4<String, String, String, String>> store(BigInteger param0) {
        final Function function = new Function(FUNC_STORE, 
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple4<String, String, String, String>>(
                new Callable<Tuple4<String, String, String, String>>() {
                    @Override
                    public Tuple4<String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple4<String, String, String, String>> findById(BigInteger _tx_id) {
        final Function function = new Function(FUNC_FINDBYID, 
                Arrays.<Type>asList(new Uint256(_tx_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple4<String, String, String, String>>(
                new Callable<Tuple4<String, String, String, String>>() {
                    @Override
                    public Tuple4<String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);

                        if (results.size() == 0){
                            return new Tuple4<String, String, String, String>(
                                    "0",
                                    "0",
                                    "0",
                                    "null");

                        }

                        return new Tuple4<String, String, String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> addSubOwner(String _address) {
        final Function function = new Function(
                FUNC_ADDSUBOWNER, 
                Arrays.<Type>asList(new Address(_address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String _newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(_newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> removeSubOwner(String _address) {
        final Function function = new Function(
                FUNC_REMOVESUBOWNER, 
                Arrays.<Type>asList(new Address(_address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<SubOwnerAddedEventResponse> getSubOwnerAddedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SUBOWNERADDED_EVENT, transactionReceipt);
        ArrayList<SubOwnerAddedEventResponse> responses = new ArrayList<SubOwnerAddedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SubOwnerAddedEventResponse typedResponse = new SubOwnerAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.subOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SubOwnerAddedEventResponse> subOwnerAddedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SubOwnerAddedEventResponse>() {
            @Override
            public SubOwnerAddedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SUBOWNERADDED_EVENT, log);
                SubOwnerAddedEventResponse typedResponse = new SubOwnerAddedEventResponse();
                typedResponse.log = log;
                typedResponse.subOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SubOwnerAddedEventResponse> subOwnerAddedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUBOWNERADDED_EVENT));
        return subOwnerAddedEventObservable(filter);
    }

    public List<SubOwnerRemovedEventResponse> getSubOwnerRemovedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SUBOWNERREMOVED_EVENT, transactionReceipt);
        ArrayList<SubOwnerRemovedEventResponse> responses = new ArrayList<SubOwnerRemovedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SubOwnerRemovedEventResponse typedResponse = new SubOwnerRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.subOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SubOwnerRemovedEventResponse> subOwnerRemovedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SubOwnerRemovedEventResponse>() {
            @Override
            public SubOwnerRemovedEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SUBOWNERREMOVED_EVENT, log);
                SubOwnerRemovedEventResponse typedResponse = new SubOwnerRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.subOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SubOwnerRemovedEventResponse> subOwnerRemovedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUBOWNERREMOVED_EVENT));
        return subOwnerRemovedEventObservable(filter);
    }

    public List<DataStoredEventResponse> getDataStoredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DATASTORED_EVENT, transactionReceipt);
        ArrayList<DataStoredEventResponse> responses = new ArrayList<DataStoredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DataStoredEventResponse typedResponse = new DataStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ix_id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.id_message = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.id_from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.id_to = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sha = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DataStoredEventResponse> dataStoredEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, DataStoredEventResponse>() {
            @Override
            public DataStoredEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DATASTORED_EVENT, log);
                DataStoredEventResponse typedResponse = new DataStoredEventResponse();
                typedResponse.log = log;
                typedResponse.ix_id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.id_message = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.id_from = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.id_to = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.sha = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<DataStoredEventResponse> dataStoredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DATASTORED_EVENT));
        return dataStoredEventObservable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OwnershipTransferredEventResponse> ownershipTransferredEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventObservable(filter);
    }

    public static RemoteCall<Storage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Storage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Storage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Storage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class SubOwnerAddedEventResponse {
        public Log log;

        public String subOwner;
    }

    public static class SubOwnerRemovedEventResponse {
        public Log log;

        public String subOwner;
    }

    public static class DataStoredEventResponse {
        public Log log;

        public BigInteger ix_id;

        public String id_message;

        public String id_from;

        public String id_to;

        public String sha;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
