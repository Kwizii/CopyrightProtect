package edu.bistu.copyright.protect.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.FunctionReturnDecoderInterface;
import org.fisco.bcos.sdk.v3.codec.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicStruct;
import org.fisco.bcos.sdk.v3.codec.datatypes.Event;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class CopyrightRepository extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610cf6806100326000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806344b0865e1461005c5780638950fb71146100855780639461ac631461009a578063b2bdfa7b146100ad578063f78b227b146100d8575b600080fd5b61006f61006a3660046107de565b6100eb565b60405161007c919061084d565b60405180910390f35b610098610093366004610939565b6102c7565b005b6100986100a83660046107de565b61036f565b6000546100c0906001600160a01b031681565b6040516001600160a01b03909116815260200161007c565b6100986100e6366004610b55565b6103e4565b61011c6040518060800160405280600063ffffffff1681526020016060815260200160608152602001600081525090565b63ffffffff8083166000908152600160208181526040928390208351608081019094528054909416835290830180549293929184019161015b90610c19565b80601f016020809104026020016040519081016040528092919081815260200182805461018790610c19565b80156101d45780601f106101a9576101008083540402835291602001916101d4565b820191906000526020600020905b8154815290600101906020018083116101b757829003601f168201915b5050505050815260200160028201805480602002602001604051908101604052809291908181526020016000905b828210156102ae57838290600052602060002001805461022190610c19565b80601f016020809104026020016040519081016040528092919081815260200182805461024d90610c19565b801561029a5780601f1061026f5761010080835404028352916020019161029a565b820191906000526020600020905b81548152906001019060200180831161027d57829003601f168201915b505050505081526020019060010190610202565b5050505081526020016003820154815250509050919050565b6000546001600160a01b031633146102fa5760405162461bcd60e51b81526004016102f190610c54565b60405180910390fd5b61036760405180608001604052808863ffffffff16815260200187878080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525050509082525060200161035a8587610c77565b81526020018390526103e4565b505050505050565b6000546001600160a01b031633146103995760405162461bcd60e51b81526004016102f190610c54565b63ffffffff811660009081526001602081905260408220805463ffffffff1916815591906103c99083018261065b565b6103d7600283016000610698565b6003820160009055505050565b8051819063ffffffff166104275760405162461bcd60e51b815260206004820152600a602482015269125b9d985b1a5908125960b21b60448201526064016102f1565b60008160200151511161046e5760405162461bcd60e51b815260206004820152600f60248201526e125b9d985b1a590818dbdb9d195b9d608a1b60448201526064016102f1565b806040015160008151811061048557610485610c84565b6020026020010151516020146104ad5760405162461bcd60e51b81526004016102f190610c9a565b80604001516001815181106104c4576104c4610c84565b6020026020010151516028146104ec5760405162461bcd60e51b81526004016102f190610c9a565b806040015160028151811061050357610503610c84565b60200260200101515160401461052b5760405162461bcd60e51b81526004016102f190610c9a565b815163ffffffff80821660009081526001602052604090205416156105865760405162461bcd60e51b8152602060048201526011602482015270115e1a5cdd19590810dbdc1e5c9a59da1d607a1b60448201526064016102f1565b6000546001600160a01b031633146105b05760405162461bcd60e51b81526004016102f190610c54565b7fc04d6bb20b39804264dbbd34b17a9053e64ca473ae01f086a13f042ed7291e0f836040516105df919061084d565b60405180910390a1825163ffffffff90811660009081526001602081815260409092208651815463ffffffff1916941693909317835581860151805187949361062c9385019201906106b6565b506040820151805161064891600284019160209091019061073a565b5060608201518160030155905050505050565b50805461066790610c19565b6000825580601f10610677575050565b601f0160209004906000526020600020908101906106959190610793565b50565b508054600082559060005260206000209081019061069591906107a8565b8280546106c290610c19565b90600052602060002090601f0160209004810192826106e4576000855561072a565b82601f106106fd57805160ff191683800117855561072a565b8280016001018555821561072a579182015b8281111561072a57825182559160200191906001019061070f565b50610736929150610793565b5090565b828054828255906000526020600020908101928215610787579160200282015b8281111561078757825180516107779184916020909101906106b6565b509160200191906001019061075a565b506107369291506107a8565b5b808211156107365760008155600101610794565b808211156107365760006107bc828261065b565b506001016107a8565b803563ffffffff811681146107d957600080fd5b919050565b6000602082840312156107f057600080fd5b6107f9826107c5565b9392505050565b6000815180845260005b818110156108265760208185018101518683018201520161080a565b81811115610838576000602083870101525b50601f01601f19169290920160200192915050565b6000602080835263ffffffff84511681840152808401516080604085015261087860a0850182610800565b6040860151601f198683038101606088015281518084529293509084019184840190600581901b8501860160005b828110156108d257848783030184526108c0828751610800565b958801959388019391506001016108a6565b5060608a015160808a01528097505050505050505092915050565b60008083601f8401126108ff57600080fd5b50813567ffffffffffffffff81111561091757600080fd5b6020830191508360208260051b850101111561093257600080fd5b9250929050565b6000806000806000806080878903121561095257600080fd5b61095b876107c5565b9550602087013567ffffffffffffffff8082111561097857600080fd5b818901915089601f83011261098c57600080fd5b81358181111561099b57600080fd5b8a60208285010111156109ad57600080fd5b6020830197508096505060408901359150808211156109cb57600080fd5b506109d889828a016108ed565b979a9699509497949695606090950135949350505050565b634e487b7160e01b600052604160045260246000fd5b6040516080810167ffffffffffffffff81118282101715610a2957610a296109f0565b60405290565b604051601f8201601f1916810167ffffffffffffffff81118282101715610a5857610a586109f0565b604052919050565b600082601f830112610a7157600080fd5b813567ffffffffffffffff811115610a8b57610a8b6109f0565b610a9e601f8201601f1916602001610a2f565b818152846020838601011115610ab357600080fd5b816020850160208301376000918101602001919091529392505050565b600067ffffffffffffffff80841115610aeb57610aeb6109f0565b8360051b6020610afc818301610a2f565b86815293509084019080840187831115610b1557600080fd5b855b83811015610b4957803585811115610b2f5760008081fd5b610b3b8a828a01610a60565b835250908201908201610b17565b50505050509392505050565b600060208284031215610b6757600080fd5b813567ffffffffffffffff80821115610b7f57600080fd5b9083019060808286031215610b9357600080fd5b610b9b610a06565b610ba4836107c5565b8152602083013582811115610bb857600080fd5b610bc487828601610a60565b602083015250604083013582811115610bdc57600080fd5b83019150601f82018613610bef57600080fd5b610bfe86833560208501610ad0565b60408201526060830135606082015280935050505092915050565b600181811c90821680610c2d57607f821691505b60208210811415610c4e57634e487b7160e01b600052602260045260246000fd5b50919050565b6020808252600990820152682737ba1027bbb732b960b91b604082015260600190565b60006107f9368484610ad0565b634e487b7160e01b600052603260045260246000fd5b6020808252600c908201526b092dcecc2d8d2c840d0c2e6d60a31b60408201526060019056fea2646970667358221220af628c646187b2ef5009908f67ffb2d20fabf2c8e17b2d8e606b8c232791837064736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610cf7806100326000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c8063265f0faf1461005c57806328e91489146100715780636dce786b146100a157806373a43dbd146100b4578063ea9dc3db146100d4575b600080fd5b61006f61006a366004610944565b6100e7565b005b600054610084906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b61006f6100af366004610a54565b61036a565b6100c76100c2366004610b0b565b61040a565b6040516100989190610b7a565b61006f6100e2366004610b0b565b6105e6565b8051819063ffffffff1661013057604051636381e58960e11b815260206004820152600a602482015269125b9d985b1a5908125960b21b60448201526064015b60405180910390fd5b60008160200151511161017857604051636381e58960e11b815260206004820152600f60248201526e125b9d985b1a590818dbdb9d195b9d608a1b6044820152606401610127565b806040015160008151811061018f5761018f610c1a565b6020026020010151516020146101b857604051636381e58960e11b815260040161012790610c30565b80604001516001815181106101cf576101cf610c1a565b6020026020010151516028146101f857604051636381e58960e11b815260040161012790610c30565b806040015160028151811061020f5761020f610c1a565b60200260200101515160401461023857604051636381e58960e11b815260040161012790610c30565b815163ffffffff808216600090815260016020526040902054161561029457604051636381e58960e11b8152602060048201526011602482015270115e1a5cdd19590810dbdc1e5c9a59da1d607a1b6044820152606401610127565b6000546001600160a01b031633146102bf57604051636381e58960e11b815260040161012790610c56565b7f06f574dfe3b94fd0100b464c6ddad08d52ace727e3a6565b073e3b2f8f45896a836040516102ee9190610b7a565b60405180910390a1825163ffffffff90811660009081526001602081815260409092208651815463ffffffff1916941693909317835581860151805187949361033b93850192019061065c565b50604082015180516103579160028401916020909101906106e0565b5060608201518160030155905050505050565b6000546001600160a01b0316331461039557604051636381e58960e11b815260040161012790610c56565b61040260405180608001604052808863ffffffff16815260200187878080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152505050908252506020016103f58587610c79565b81526020018390526100e7565b505050505050565b61043b6040518060800160405280600063ffffffff1681526020016060815260200160608152602001600081525090565b63ffffffff8083166000908152600160208181526040928390208351608081019094528054909416835290830180549293929184019161047a90610c86565b80601f01602080910402602001604051908101604052809291908181526020018280546104a690610c86565b80156104f35780601f106104c8576101008083540402835291602001916104f3565b820191906000526020600020905b8154815290600101906020018083116104d657829003601f168201915b5050505050815260200160028201805480602002602001604051908101604052809291908181526020016000905b828210156105cd57838290600052602060002001805461054090610c86565b80601f016020809104026020016040519081016040528092919081815260200182805461056c90610c86565b80156105b95780601f1061058e576101008083540402835291602001916105b9565b820191906000526020600020905b81548152906001019060200180831161059c57829003601f168201915b505050505081526020019060010190610521565b5050505081526020016003820154815250509050919050565b6000546001600160a01b0316331461061157604051636381e58960e11b815260040161012790610c56565b63ffffffff811660009081526001602081905260408220805463ffffffff19168155919061064190830182610739565b61064f600283016000610776565b6003820160009055505050565b82805461066890610c86565b90600052602060002090601f01602090048101928261068a57600085556106d0565b82601f106106a357805160ff19168380011785556106d0565b828001600101855582156106d0579182015b828111156106d05782518255916020019190600101906106b5565b506106dc929150610794565b5090565b82805482825590600052602060002090810192821561072d579160200282015b8281111561072d578251805161071d91849160209091019061065c565b5091602001919060010190610700565b506106dc9291506107a9565b50805461074590610c86565b6000825580601f10610755575050565b601f0160209004906000526020600020908101906107739190610794565b50565b508054600082559060005260206000209081019061077391906107a9565b5b808211156106dc5760008155600101610795565b808211156106dc5760006107bd8282610739565b506001016107a9565b63b95aa35560e01b600052604160045260246000fd5b6040516080810167ffffffffffffffff811182821017156107ff576107ff6107c6565b60405290565b604051601f8201601f1916810167ffffffffffffffff8111828210171561082e5761082e6107c6565b604052919050565b803563ffffffff8116811461084a57600080fd5b919050565b600082601f83011261086057600080fd5b813567ffffffffffffffff81111561087a5761087a6107c6565b61088d601f8201601f1916602001610805565b8181528460208386010111156108a257600080fd5b816020850160208301376000918101602001919091529392505050565b600067ffffffffffffffff808411156108da576108da6107c6565b8360051b60206108eb818301610805565b8681529350908401908084018783111561090457600080fd5b855b838110156109385780358581111561091e5760008081fd5b61092a8a828a0161084f565b835250908201908201610906565b50505050509392505050565b60006020828403121561095657600080fd5b813567ffffffffffffffff8082111561096e57600080fd5b908301906080828603121561098257600080fd5b61098a6107dc565b61099383610836565b81526020830135828111156109a757600080fd5b6109b38782860161084f565b6020830152506040830135828111156109cb57600080fd5b83019150601f820186136109de57600080fd5b6109ed868335602085016108bf565b60408201526060830135606082015280935050505092915050565b60008083601f840112610a1a57600080fd5b50813567ffffffffffffffff811115610a3257600080fd5b6020830191508360208260051b8501011115610a4d57600080fd5b9250929050565b60008060008060008060808789031215610a6d57600080fd5b610a7687610836565b9550602087013567ffffffffffffffff80821115610a9357600080fd5b818901915089601f830112610aa757600080fd5b813581811115610ab657600080fd5b8a6020828501011115610ac857600080fd5b602083019750809650506040890135915080821115610ae657600080fd5b50610af389828a01610a08565b979a9699509497949695606090950135949350505050565b600060208284031215610b1d57600080fd5b610b2682610836565b9392505050565b6000815180845260005b81811015610b5357602081850181015186830182015201610b37565b81811115610b65576000602083870101525b50601f01601f19169290920160200192915050565b6000602080835263ffffffff845116818401528084015160806040850152610ba560a0850182610b2d565b6040860151601f198683038101606088015281518084529293509084019184840190600581901b8501860160005b82811015610bff5784878303018452610bed828751610b2d565b95880195938801939150600101610bd3565b5060608a015160808a01528097505050505050505092915050565b63b95aa35560e01b600052603260045260246000fd5b6020808252600c908201526b092dcecc2d8d2c840d0c2e6d60a31b604082015260600190565b6020808252600990820152682737ba1027bbb732b960b91b604082015260600190565b6000610b263684846108bf565b600181811c90821680610c9a57607f821691505b60208210811415610cbb5763b95aa35560e01b600052602260045260246000fd5b5091905056fea2646970667358221220097d911f07703c8f9a711c7f5fca7a19548ddaa2f3ad9d6e0025fec666894cae64736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"components\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"indexed\":false,\"internalType\":\"struct CopyrightRepository.CopyrightSol\",\"name\":\"copyright\",\"type\":\"tuple\"}],\"name\":\"CopyrightStored\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"_owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[2998794875,686363785],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"}],\"name\":\"delCopyright\",\"outputs\":[],\"selector\":[2489429091,3936207835],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"}],\"name\":\"getCopyright\",\"outputs\":[{\"components\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"internalType\":\"struct CopyrightRepository.CopyrightSol\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[1152419422,1940143549],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"name\":\"requestStore\",\"outputs\":[],\"selector\":[2303785841,1842247787],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"components\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"internalType\":\"struct CopyrightRepository.CopyrightSol\",\"name\":\"copyright\",\"type\":\"tuple\"}],\"name\":\"storeCopyright\",\"outputs\":[],\"selector\":[4153090683,643764143],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC__OWNER = "_owner";

    public static final String FUNC_DELCOPYRIGHT = "delCopyright";

    public static final String FUNC_GETCOPYRIGHT = "getCopyright";

    public static final String FUNC_REQUESTSTORE = "requestStore";

    public static final String FUNC_STORECOPYRIGHT = "storeCopyright";

    public static final Event COPYRIGHTSTORED_EVENT = new Event("CopyrightStored",
            Arrays.<TypeReference<?>>asList(new TypeReference<CopyrightSol>() {
            }));

    public static final FunctionReturnDecoderInterface functionReturnDecoder = new FunctionReturnDecoder();


    protected CopyrightRepository(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<CopyrightStoredEventResponse> getCopyrightStoredEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(COPYRIGHTSTORED_EVENT, transactionReceipt);
        ArrayList<CopyrightStoredEventResponse> responses = new ArrayList<CopyrightStoredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CopyrightStoredEventResponse typedResponse = new CopyrightStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.copyright = (CopyrightSol) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public String _owner() throws ContractException {
        final Function function = new Function(FUNC__OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt delCopyright(BigInteger id) {
        final Function function = new Function(
                FUNC_DELCOPYRIGHT,
                Arrays.<Type>asList(new Uint32(id)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForDelCopyright(BigInteger id) {
        final Function function = new Function(
                FUNC_DELCOPYRIGHT,
                Arrays.<Type>asList(new Uint32(id)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String delCopyright(BigInteger id, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_DELCOPYRIGHT,
                Arrays.<Type>asList(new Uint32(id)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<BigInteger> getDelCopyrightInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_DELCOPYRIGHT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {
                }));
        List<Type> results = functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
        );
    }

    public CopyrightSol getCopyright(BigInteger id) throws ContractException {
        final Function function = new Function(FUNC_GETCOPYRIGHT,
                Arrays.<Type>asList(new Uint32(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<CopyrightSol>() {
                }));
        return executeCallWithSingleValueReturn(function, CopyrightSol.class);
    }

    public TransactionReceipt requestStore(BigInteger id, String content, List<String> hashes,
                                           BigInteger createTime) {
        final Function function = new Function(
                FUNC_REQUESTSTORE,
                Arrays.<Type>asList(new Uint32(id),
                        new Utf8String(content),
                        new DynamicArray<Utf8String>(
                                Utf8String.class,
                                org.fisco.bcos.sdk.v3.codec.Utils.typeMap(hashes, Utf8String.class)),
                        new Uint256(createTime)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRequestStore(BigInteger id, String content,
                                                      List<String> hashes, BigInteger createTime) {
        final Function function = new Function(
                FUNC_REQUESTSTORE,
                Arrays.<Type>asList(new Uint32(id),
                        new Utf8String(content),
                        new DynamicArray<Utf8String>(
                                Utf8String.class,
                                org.fisco.bcos.sdk.v3.codec.Utils.typeMap(hashes, Utf8String.class)),
                        new Uint256(createTime)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String requestStore(BigInteger id, String content, List<String> hashes,
                               BigInteger createTime, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REQUESTSTORE,
                Arrays.<Type>asList(new Uint32(id),
                        new Utf8String(content),
                        new DynamicArray<Utf8String>(
                                Utf8String.class,
                                org.fisco.bcos.sdk.v3.codec.Utils.typeMap(hashes, Utf8String.class)),
                        new Uint256(createTime)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public static Tuple4<BigInteger, String, List<String>, BigInteger> getRequestStoreInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REQUESTSTORE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<DynamicArray<Utf8String>>() {
                }, new TypeReference<Uint256>() {
                }));
        List<Type> results = functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple4<BigInteger, String, List<String>, BigInteger>(

                (BigInteger) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                convertToNative((List<Utf8String>) results.get(2).getValue()),
                (BigInteger) results.get(3).getValue()
        );
    }

    public TransactionReceipt storeCopyright(CopyrightSol copyright) {
        final Function function = new Function(
                FUNC_STORECOPYRIGHT,
                Arrays.<Type>asList(copyright),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForStoreCopyright(CopyrightSol copyright) {
        final Function function = new Function(
                FUNC_STORECOPYRIGHT,
                Arrays.<Type>asList(copyright),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String storeCopyright(CopyrightSol copyright, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_STORECOPYRIGHT,
                Arrays.<Type>asList(copyright),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<CopyrightSol> getStoreCopyrightInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_STORECOPYRIGHT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<CopyrightSol>() {
                }));
        List<Type> results = functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<CopyrightSol>(

                (CopyrightSol) results.get(0)
        );
    }

    public static CopyrightRepository load(String contractAddress, Client client,
                                           CryptoKeyPair credential) {
        return new CopyrightRepository(contractAddress, client, credential);
    }

    public static CopyrightRepository deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(CopyrightRepository.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }

    public static class CopyrightSol extends DynamicStruct {
        public BigInteger id;

        public String content;

        public List<String> hashes;

        public BigInteger createTime;

        public CopyrightSol(Uint32 id, Utf8String content, DynamicArray<Utf8String> hashes,
                            Uint256 createTime) {
            super(id, content, hashes, createTime);
            this.id = id.getValue();
            this.content = content.getValue();
            this.hashes = hashes.getValue().stream().map(Utf8String::getValue).collect(java.util.stream.Collectors.toList());
            this.createTime = createTime.getValue();
        }

        public CopyrightSol(BigInteger id, String content, List<String> hashes,
                            BigInteger createTime) {
            super(new Uint32(id), new Utf8String(content), new DynamicArray<Utf8String>(Utf8String.class, hashes.stream().map(Utf8String::new).collect(java.util.stream.Collectors.toList())), new Uint256(createTime));
            this.id = id;
            this.content = content;
            this.hashes = hashes;
            this.createTime = createTime;
        }
    }

    public static class CopyrightStoredEventResponse {
        public TransactionReceipt.Logs log;

        public CopyrightSol copyright;
    }
}
