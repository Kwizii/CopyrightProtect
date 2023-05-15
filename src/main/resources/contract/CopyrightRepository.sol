// SPDX-License-Identifier: Apache-2.0
pragma solidity ^0.8.0;

import "./Authentication.sol";

contract CopyrightRepository is Authentication {
    struct CopyrightSol {
        uint32 id;
        string description;
        string content;
        string[] hashes;
        uint256 createTime;
    }

    mapping(address => CopyrightSol) private _copyrights;

    event CopyrightStored(CopyrightSol copyright);

    event checkSender(address);

    function requestStore(
        uint32 id,
        string calldata description,
        string calldata content,
        string[] calldata hashes,
        uint256 createTime
    ) public
    isOwner
    returns
    (address copyAddress)
    {
        return storeCopyright(CopyrightSol(id, description, content, hashes, createTime));
    }

    function storeCopyright(CopyrightSol memory copyright)
    public
    checkCopyright(copyright)
    isOwner
    returns (address)
    {
        address copyAddress = address(
            uint160(
                uint256(
                    keccak256(
                        abi.encodePacked(
                            _owner,
                            copyright.hashes[0],
                            copyright.hashes[1],
                            copyright.hashes[2],
                            copyright.createTime
                        )
                    )
                )
            )
        );
        emit CopyrightStored(copyright);
        _copyrights[copyAddress] = copyright;
        return copyAddress;
    }

    function getCopyright(address copyAddress)
    public
    view
    returns (CopyrightSol memory)
    {
        require(copyAddress != address(0), "Address is empty");
        CopyrightSol storage copyright = _copyrights[copyAddress];
        require(copyright.id != 0, "Address is empty");
        return copyright;
    }

    modifier checkCopyright(CopyrightSol memory copyright) {
        require(copyright.id != 0, "Invalid id");
        require(bytes(copyright.description).length > 0, "Invalid description");
        require(bytes(copyright.content).length > 0, "Invalid content");
        require(bytes(copyright.hashes[0]).length == 32, "Invalid hash");
        require(bytes(copyright.hashes[1]).length == 40, "Invalid hash");
        require(bytes(copyright.hashes[2]).length == 64, "Invalid hash");
        _;
    }
}
