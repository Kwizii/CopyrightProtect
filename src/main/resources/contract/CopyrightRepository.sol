// SPDX-License-Identifier: Apache-2.0
pragma solidity ^0.8.0;

import "./Authentication.sol";

contract CopyrightRepository is Authentication {
    struct CopyrightSol {
        uint32 id;
        string content;
        string[] hashes;
        uint256 createTime;
    }

    mapping(uint32 => CopyrightSol) private _copyrights;

    event CopyrightStored(CopyrightSol copyright);

    function requestStore(
        uint32 id,
        string calldata content,
        string[] calldata hashes,
        uint256 createTime
    ) public isOwner {
        return storeCopyright(CopyrightSol(id, content, hashes, createTime));
    }

    function storeCopyright(CopyrightSol memory copyright)
        public
        checkCopyright(copyright)
        notExist(copyright.id)
        isOwner
    {
        emit CopyrightStored(copyright);
        _copyrights[copyright.id] = copyright;
    }

    function delCopyright(uint32 id) public isOwner {
        delete _copyrights[id];
    }

    function getCopyright(uint32 id) public view returns (CopyrightSol memory) {
        return _copyrights[id];
    }

    modifier checkCopyright(CopyrightSol memory copyright) {
        require(copyright.id > 0, "Invalid Id");
        require(bytes(copyright.content).length > 0, "Invalid content");
        require(bytes(copyright.hashes[0]).length == 32, "Invalid hash");
        require(bytes(copyright.hashes[1]).length == 40, "Invalid hash");
        require(bytes(copyright.hashes[2]).length == 64, "Invalid hash");
        _;
    }

    modifier notExist(uint32 id) {
        require(_copyrights[id].id == 0, "Existed Copyright");
        _;
    }
}
