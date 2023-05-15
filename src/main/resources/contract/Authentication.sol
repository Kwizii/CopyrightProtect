// SPDX-License-Identifier: Apache-2.0
pragma solidity ^0.8.0;

contract Authentication {
    address public _owner;

    constructor() {
        _owner = msg.sender;
    }

    modifier isOwner(){
        require(msg.sender == _owner, "Not Owner");
        _;
    }
}
