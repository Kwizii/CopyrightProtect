package edu.bistu.copyright.protect.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
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
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class CopyrightRepository extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610f6e806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806314bc50791461005157806322758c4c1461008157806393b2eda914610094578063b2bdfa7b146100b4575b600080fd5b61006461005f366004610b0d565b6100c7565b6040516001600160a01b0390911681526020015b60405180910390f35b61006461008f366004610c29565b610416565b6100a76100a2366004610d0d565b610512565b6040516100789190610d92565b600054610064906001600160a01b031681565b8051600090829063ffffffff166101125760405162461bcd60e51b815260206004820152600a602482015269125b9d985b1a59081a5960b21b60448201526064015b60405180910390fd5b60008160200151511161015d5760405162461bcd60e51b815260206004820152601360248201527224b73b30b634b2103232b9b1b934b83a34b7b760691b6044820152606401610109565b6000816040015151116101a45760405162461bcd60e51b815260206004820152600f60248201526e125b9d985b1a590818dbdb9d195b9d608a1b6044820152606401610109565b80606001516000815181106101bb576101bb610e4d565b6020026020010151516020146101e35760405162461bcd60e51b815260040161010990610e63565b80606001516001815181106101fa576101fa610e4d565b6020026020010151516028146102225760405162461bcd60e51b815260040161010990610e63565b806060015160028151811061023957610239610e4d565b6020026020010151516040146102615760405162461bcd60e51b815260040161010990610e63565b6000546001600160a01b031633146102a75760405162461bcd60e51b81526020600482015260096024820152682737ba1027bbb732b960b91b6044820152606401610109565b60008054606085015180516001600160a01b039092169183906102cc576102cc610e4d565b602002602001015185606001516001815181106102eb576102eb610e4d565b6020026020010151866060015160028151811061030a5761030a610e4d565b6020026020010151876080015160405160200161032b959493929190610e89565b6040516020818303038152906040528051906020012060001c90507f8515d6fc785df2f6a1cc031f46a5e8d4c6a14d9c6b1018641da173b4cce1277d846040516103759190610d92565b60405180910390a16001600160a01b03811660009081526001602081815260409092208651815463ffffffff191663ffffffff909116178155868301518051889492936103c79390850192019061081c565b50604082015180516103e391600284019160209091019061081c565b50606082015180516103ff9160038401916020909101906108a0565b506080919091015160049091015591505b50919050565b600080546001600160a01b0316331461045d5760405162461bcd60e51b81526020600482015260096024820152682737ba1027bbb732b960b91b6044820152606401610109565b6105056040518060a001604052808b63ffffffff1681526020018a8a8080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250505090825250604080516020601f8b0181900481028201810190925289815291810191908a908a90819084018382808284376000920191909152505050908252506020016104f88688610ef6565b81526020018490526100c7565b9998505050505050505050565b61054a6040518060a00160405280600063ffffffff168152602001606081526020016060815260200160608152602001600081525090565b6001600160a01b0382166105935760405162461bcd60e51b815260206004820152601060248201526f4164647265737320697320656d70747960801b6044820152606401610109565b6001600160a01b0382166000908152600160205260409020805463ffffffff166105f25760405162461bcd60e51b815260206004820152601060248201526f4164647265737320697320656d70747960801b6044820152606401610109565b6040805160a08101909152815463ffffffff1681526001820180548391602084019161061d90610f03565b80601f016020809104026020016040519081016040528092919081815260200182805461064990610f03565b80156106965780601f1061066b57610100808354040283529160200191610696565b820191906000526020600020905b81548152906001019060200180831161067957829003601f168201915b505050505081526020016002820180546106af90610f03565b80601f01602080910402602001604051908101604052809291908181526020018280546106db90610f03565b80156107285780601f106106fd57610100808354040283529160200191610728565b820191906000526020600020905b81548152906001019060200180831161070b57829003601f168201915b5050505050815260200160038201805480602002602001604051908101604052809291908181526020016000905b8282101561080257838290600052602060002001805461077590610f03565b80601f01602080910402602001604051908101604052809291908181526020018280546107a190610f03565b80156107ee5780601f106107c3576101008083540402835291602001916107ee565b820191906000526020600020905b8154815290600101906020018083116107d157829003601f168201915b505050505081526020019060010190610756565b505050508152602001600482015481525050915050919050565b82805461082890610f03565b90600052602060002090601f01602090048101928261084a5760008555610890565b82601f1061086357805160ff1916838001178555610890565b82800160010185558215610890579182015b82811115610890578251825591602001919060010190610875565b5061089c9291506108f9565b5090565b8280548282559060005260206000209081019282156108ed579160200282015b828111156108ed57825180516108dd91849160209091019061081c565b50916020019190600101906108c0565b5061089c92915061090e565b5b8082111561089c57600081556001016108fa565b8082111561089c576000610922828261092b565b5060010161090e565b50805461093790610f03565b6000825580601f10610947575050565b601f01602090049060005260206000209081019061096591906108f9565b50565b634e487b7160e01b600052604160045260246000fd5b60405160a0810167ffffffffffffffff811182821017156109a1576109a1610968565b60405290565b604051601f8201601f1916810167ffffffffffffffff811182821017156109d0576109d0610968565b604052919050565b803563ffffffff811681146109ec57600080fd5b919050565b600082601f830112610a0257600080fd5b813567ffffffffffffffff811115610a1c57610a1c610968565b610a2f601f8201601f19166020016109a7565b818152846020838601011115610a4457600080fd5b816020850160208301376000918101602001919091529392505050565b600067ffffffffffffffff80841115610a7c57610a7c610968565b8360051b6020610a8d8183016109a7565b86815293509084019080840187831115610aa657600080fd5b855b83811015610ada57803585811115610ac05760008081fd5b610acc8a828a016109f1565b835250908201908201610aa8565b50505050509392505050565b600082601f830112610af757600080fd5b610b0683833560208501610a61565b9392505050565b600060208284031215610b1f57600080fd5b813567ffffffffffffffff80821115610b3757600080fd5b9083019060a08286031215610b4b57600080fd5b610b5361097e565b610b5c836109d8565b8152602083013582811115610b7057600080fd5b610b7c878286016109f1565b602083015250604083013582811115610b9457600080fd5b610ba0878286016109f1565b604083015250606083013582811115610bb857600080fd5b610bc487828601610ae6565b6060830152506080830135608082015280935050505092915050565b60008083601f840112610bf257600080fd5b50813567ffffffffffffffff811115610c0a57600080fd5b602083019150836020828501011115610c2257600080fd5b9250929050565b60008060008060008060008060a0898b031215610c4557600080fd5b610c4e896109d8565b9750602089013567ffffffffffffffff80821115610c6b57600080fd5b610c778c838d01610be0565b909950975060408b0135915080821115610c9057600080fd5b610c9c8c838d01610be0565b909750955060608b0135915080821115610cb557600080fd5b818b0191508b601f830112610cc957600080fd5b813581811115610cd857600080fd5b8c60208260051b8501011115610ced57600080fd5b602083019550809450505050608089013590509295985092959890939650565b600060208284031215610d1f57600080fd5b81356001600160a01b0381168114610b0657600080fd5b60005b83811015610d51578181015183820152602001610d39565b83811115610d60576000848401525b50505050565b60008151808452610d7e816020860160208601610d36565b601f01601f19169290920160200192915050565b6000602080835263ffffffff845116818401528084015160a06040850152610dbd60c0850182610d66565b90506040850151601f1980868403016060870152610ddb8383610d66565b60608801518782038301608089015280518083529194508501925084840190600581901b8501860160005b82811015610e325784878303018452610e20828751610d66565b95880195938801939150600101610e06565b5060808a015160a08a01528097505050505050505092915050565b634e487b7160e01b600052603260045260246000fd5b6020808252600c908201526b092dcecc2d8d2c840d0c2e6d60a31b604082015260600190565b6bffffffffffffffffffffffff198660601b16815260008551610eb3816014850160208a01610d36565b855190830190610eca816014840160208a01610d36565b8551910190610ee0816014840160208901610d36565b0160148101939093525050603401949350505050565b6000610b06368484610a61565b600181811c90821680610f1757607f821691505b6020821081141561041057634e487b7160e01b600052602260045260246000fdfea264697066735822122048c1460ce45b6e52fa91aeae90d72c78b7a8172f97eda8acf8a58edc92e851e164736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50600080546001600160a01b03191633179055610f78806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806328e91489146100515780633d07a9d81461008157806348267b62146100945780637a4eeb6b146100b4575b600080fd5b600054610064906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b61006461008f366004610b17565b6100c7565b6100a76100a2366004610bea565b61041d565b6040516100789190610c6f565b6100646100c2366004610d73565b610729565b8051600090829063ffffffff1661011357604051636381e58960e11b815260206004820152600a602482015269125b9d985b1a59081a5960b21b60448201526064015b60405180910390fd5b60008160200151511161015f57604051636381e58960e11b815260206004820152601360248201527224b73b30b634b2103232b9b1b934b83a34b7b760691b604482015260640161010a565b6000816040015151116101a757604051636381e58960e11b815260206004820152600f60248201526e125b9d985b1a590818dbdb9d195b9d608a1b604482015260640161010a565b80606001516000815181106101be576101be610e57565b6020026020010151516020146101e757604051636381e58960e11b815260040161010a90610e6d565b80606001516001815181106101fe576101fe610e57565b60200260200101515160281461022757604051636381e58960e11b815260040161010a90610e6d565b806060015160028151811061023e5761023e610e57565b60200260200101515160401461026757604051636381e58960e11b815260040161010a90610e6d565b6000546001600160a01b031633146102ae57604051636381e58960e11b81526020600482015260096024820152682737ba1027bbb732b960b91b604482015260640161010a565b60008054606085015180516001600160a01b039092169183906102d3576102d3610e57565b602002602001015185606001516001815181106102f2576102f2610e57565b6020026020010151866060015160028151811061031157610311610e57565b60200260200101518760800151604051602001610332959493929190610e93565b6040516020818303038152906040528051906020012060001c90507fa55e856d05ee2a0a2da120d35a17d3ede2c9ae8ec8568e73c8a3c7b4a8e0ff408460405161037c9190610c6f565b60405180910390a16001600160a01b03811660009081526001602081815260409092208651815463ffffffff191663ffffffff909116178155868301518051889492936103ce93908501920190610826565b50604082015180516103ea916002840191602090910190610826565b50606082015180516104069160038401916020909101906108aa565b506080919091015160049091015591505b50919050565b6104556040518060a00160405280600063ffffffff168152602001606081526020016060815260200160608152602001600081525090565b6001600160a01b03821661049f57604051636381e58960e11b815260206004820152601060248201526f4164647265737320697320656d70747960801b604482015260640161010a565b6001600160a01b0382166000908152600160205260409020805463ffffffff166104ff57604051636381e58960e11b815260206004820152601060248201526f4164647265737320697320656d70747960801b604482015260640161010a565b6040805160a08101909152815463ffffffff1681526001820180548391602084019161052a90610f00565b80601f016020809104026020016040519081016040528092919081815260200182805461055690610f00565b80156105a35780601f10610578576101008083540402835291602001916105a3565b820191906000526020600020905b81548152906001019060200180831161058657829003601f168201915b505050505081526020016002820180546105bc90610f00565b80601f01602080910402602001604051908101604052809291908181526020018280546105e890610f00565b80156106355780601f1061060a57610100808354040283529160200191610635565b820191906000526020600020905b81548152906001019060200180831161061857829003601f168201915b5050505050815260200160038201805480602002602001604051908101604052809291908181526020016000905b8282101561070f57838290600052602060002001805461068290610f00565b80601f01602080910402602001604051908101604052809291908181526020018280546106ae90610f00565b80156106fb5780601f106106d0576101008083540402835291602001916106fb565b820191906000526020600020905b8154815290600101906020018083116106de57829003601f168201915b505050505081526020019060010190610663565b505050508152602001600482015481525050915050919050565b600080546001600160a01b0316331461077157604051636381e58960e11b81526020600482015260096024820152682737ba1027bbb732b960b91b604482015260640161010a565b6108196040518060a001604052808b63ffffffff1681526020018a8a8080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250505090825250604080516020601f8b0181900481028201810190925289815291810191908a908a908190840183828082843760009201919091525050509082525060200161080c8688610f35565b81526020018490526100c7565b9998505050505050505050565b82805461083290610f00565b90600052602060002090601f016020900481019282610854576000855561089a565b82601f1061086d57805160ff191683800117855561089a565b8280016001018555821561089a579182015b8281111561089a57825182559160200191906001019061087f565b506108a6929150610903565b5090565b8280548282559060005260206000209081019282156108f7579160200282015b828111156108f757825180516108e7918491602090910190610826565b50916020019190600101906108ca565b506108a6929150610918565b5b808211156108a65760008155600101610904565b808211156108a657600061092c8282610935565b50600101610918565b50805461094190610f00565b6000825580601f10610951575050565b601f01602090049060005260206000209081019061096f9190610903565b50565b63b95aa35560e01b600052604160045260246000fd5b60405160a0810167ffffffffffffffff811182821017156109ab576109ab610972565b60405290565b604051601f8201601f1916810167ffffffffffffffff811182821017156109da576109da610972565b604052919050565b803563ffffffff811681146109f657600080fd5b919050565b600082601f830112610a0c57600080fd5b813567ffffffffffffffff811115610a2657610a26610972565b610a39601f8201601f19166020016109b1565b818152846020838601011115610a4e57600080fd5b816020850160208301376000918101602001919091529392505050565b600067ffffffffffffffff80841115610a8657610a86610972565b8360051b6020610a978183016109b1565b86815293509084019080840187831115610ab057600080fd5b855b83811015610ae457803585811115610aca5760008081fd5b610ad68a828a016109fb565b835250908201908201610ab2565b50505050509392505050565b600082601f830112610b0157600080fd5b610b1083833560208501610a6b565b9392505050565b600060208284031215610b2957600080fd5b813567ffffffffffffffff80821115610b4157600080fd5b9083019060a08286031215610b5557600080fd5b610b5d610988565b610b66836109e2565b8152602083013582811115610b7a57600080fd5b610b86878286016109fb565b602083015250604083013582811115610b9e57600080fd5b610baa878286016109fb565b604083015250606083013582811115610bc257600080fd5b610bce87828601610af0565b6060830152506080830135608082015280935050505092915050565b600060208284031215610bfc57600080fd5b81356001600160a01b0381168114610b1057600080fd5b60005b83811015610c2e578181015183820152602001610c16565b83811115610c3d576000848401525b50505050565b60008151808452610c5b816020860160208601610c13565b601f01601f19169290920160200192915050565b6000602080835263ffffffff845116818401528084015160a06040850152610c9a60c0850182610c43565b90506040850151601f1980868403016060870152610cb88383610c43565b60608801518782038301608089015280518083529194508501925084840190600581901b8501860160005b82811015610d0f5784878303018452610cfd828751610c43565b95880195938801939150600101610ce3565b5060808a015160a08a01528097505050505050505092915050565b60008083601f840112610d3c57600080fd5b50813567ffffffffffffffff811115610d5457600080fd5b602083019150836020828501011115610d6c57600080fd5b9250929050565b60008060008060008060008060a0898b031215610d8f57600080fd5b610d98896109e2565b9750602089013567ffffffffffffffff80821115610db557600080fd5b610dc18c838d01610d2a565b909950975060408b0135915080821115610dda57600080fd5b610de68c838d01610d2a565b909750955060608b0135915080821115610dff57600080fd5b818b0191508b601f830112610e1357600080fd5b813581811115610e2257600080fd5b8c60208260051b8501011115610e3757600080fd5b602083019550809450505050608089013590509295985092959890939650565b63b95aa35560e01b600052603260045260246000fd5b6020808252600c908201526b092dcecc2d8d2c840d0c2e6d60a31b604082015260600190565b6bffffffffffffffffffffffff198660601b16815260008551610ebd816014850160208a01610c13565b855190830190610ed4816014840160208a01610c13565b8551910190610eea816014840160208901610c13565b0160148101939093525050603401949350505050565b600181811c90821680610f1457607f821691505b602082108114156104175763b95aa35560e01b600052602260045260246000fd5b6000610b10368484610a6b56fea2646970667358221220e8daa2dcb084ca4c22c9e5bed352a2253c14dad254435f29f80eca54085c55b764736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"components\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"indexed\":false,\"internalType\":\"struct CopyrightRepository.CopyrightSol\",\"name\":\"copyright\",\"type\":\"tuple\"}],\"name\":\"CopyrightStored\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"name\":\"checkSender\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"_owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[2998794875,686363785],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"copyAddress\",\"type\":\"address\"}],\"name\":\"getCopyright\",\"outputs\":[{\"components\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"internalType\":\"struct CopyrightRepository.CopyrightSol\",\"name\":\"\",\"type\":\"tuple\"}],\"selector\":[2477977001,1210481506],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"name\":\"requestStore\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"copyAddress\",\"type\":\"address\"}],\"selector\":[578128972,2051992427],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"components\":[{\"internalType\":\"uint32\",\"name\":\"id\",\"type\":\"uint32\"},{\"internalType\":\"string\",\"name\":\"description\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"content\",\"type\":\"string\"},{\"internalType\":\"string[]\",\"name\":\"hashes\",\"type\":\"string[]\"},{\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"internalType\":\"struct CopyrightRepository.CopyrightSol\",\"name\":\"copyright\",\"type\":\"tuple\"}],\"name\":\"storeCopyright\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[347885689,1023912408],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC__OWNER = "_owner";

    public static final String FUNC_GETCOPYRIGHT = "getCopyright";

    public static final String FUNC_REQUESTSTORE = "requestStore";

    public static final String FUNC_STORECOPYRIGHT = "storeCopyright";

    public static final Event COPYRIGHTSTORED_EVENT = new Event("CopyrightStored", 
            Arrays.<TypeReference<?>>asList(new TypeReference<CopyrightSol>() {}));
    ;

    public static final Event CHECKSENDER_EVENT = new Event("checkSender", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

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

    public List<CheckSenderEventResponse> getCheckSenderEvents(
            TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CHECKSENDER_EVENT, transactionReceipt);
        ArrayList<CheckSenderEventResponse> responses = new ArrayList<CheckSenderEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CheckSenderEventResponse typedResponse = new CheckSenderEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.checkSenderParam0 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public String _owner() throws ContractException {
        final Function function = new Function(FUNC__OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public CopyrightSol getCopyright(String copyAddress) throws ContractException {
        final Function function = new Function(FUNC_GETCOPYRIGHT, 
                Arrays.<Type>asList(new Address(copyAddress)),
                Arrays.<TypeReference<?>>asList(new TypeReference<CopyrightSol>() {}));
        return executeCallWithSingleValueReturn(function, CopyrightSol.class);
    }

    public TransactionReceipt requestStore(BigInteger id, String description, String content,
            List<String> hashes, BigInteger createTime) {
        final Function function = new Function(
                FUNC_REQUESTSTORE, 
                Arrays.<Type>asList(new Uint32(id),
                new Utf8String(description),
                new Utf8String(content),
                new DynamicArray<Utf8String>(
                        Utf8String.class,
                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(hashes, Utf8String.class)),
                new Uint256(createTime)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRequestStore(BigInteger id, String description,
            String content, List<String> hashes, BigInteger createTime) {
        final Function function = new Function(
                FUNC_REQUESTSTORE, 
                Arrays.<Type>asList(new Uint32(id),
                new Utf8String(description),
                new Utf8String(content),
                new DynamicArray<Utf8String>(
                        Utf8String.class,
                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(hashes, Utf8String.class)),
                new Uint256(createTime)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String requestStore(BigInteger id, String description, String content,
            List<String> hashes, BigInteger createTime, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REQUESTSTORE, 
                Arrays.<Type>asList(new Uint32(id),
                new Utf8String(description),
                new Utf8String(content),
                new DynamicArray<Utf8String>(
                        Utf8String.class,
                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(hashes, Utf8String.class)),
                new Uint256(createTime)),
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple5<BigInteger, String, String, List<String>, BigInteger> getRequestStoreInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REQUESTSTORE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<DynamicArray<Utf8String>>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple5<BigInteger, String, String, List<String>, BigInteger>(

                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                convertToNative((List<Utf8String>) results.get(3).getValue()), 
                (BigInteger) results.get(4).getValue()
                );
    }

    public Tuple1<String> getRequestStoreOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_REQUESTSTORE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
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
                Arrays.<TypeReference<?>>asList(new TypeReference<CopyrightSol>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<CopyrightSol>(

                (CopyrightSol) results.get(0)
                );
    }

    public Tuple1<String> getStoreCopyrightOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_STORECOPYRIGHT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
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

        public String description;

        public String content;

        public List<String> hashes;

        public BigInteger createTime;

        public CopyrightSol(Uint32 id, Utf8String description, Utf8String content,
                DynamicArray<Utf8String> hashes, Uint256 createTime) {
            super(id,description,content,hashes,createTime);
            this.id = id.getValue();
            this.description = description.getValue();
            this.content = content.getValue();
            this.hashes = hashes.getValue().stream().map(Utf8String::getValue).collect(java.util.stream.Collectors.toList());
            this.createTime = createTime.getValue();
        }

        public CopyrightSol(BigInteger id, String description, String content, List<String> hashes,
                BigInteger createTime) {
            super(new Uint32(id),new Utf8String(description),new Utf8String(content),new DynamicArray<Utf8String>(Utf8String.class, hashes.stream().map(Utf8String::new).collect(java.util.stream.Collectors.toList())),new Uint256(createTime));
            this.id = id;
            this.description = description;
            this.content = content;
            this.hashes = hashes;
            this.createTime = createTime;
        }
    }

    public static class CopyrightStoredEventResponse {
        public TransactionReceipt.Logs log;

        public CopyrightSol copyright;
    }

    public static class CheckSenderEventResponse {
        public TransactionReceipt.Logs log;

        public String checkSenderParam0;
    }
}
