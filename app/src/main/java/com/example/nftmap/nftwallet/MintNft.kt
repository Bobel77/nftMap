package com.example.nftmap.nftwallet


/*import org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import java.math.BigInteger*/

/*fun test() {
    val privateKeyHex = "2629fb39797362a82dd83a7261181f6ec7fe0980572f2b32cca0279765759f68"
    val mnemonic = "mistake ribbon glass ticket elite apple noodle minimum sunset trouble option talk"
    val path = intArrayOf(44 or HARDENED_BIT, 60 or HARDENED_BIT, 0 or HARDENED_BIT, 0, 0)

*//*    val seed = MnemonicUtils.generateSeed(mnemonic,"")
    val masterKeyPair = Bip32ECKeyPair.generateKeyPair(seed)
    val bip44Keypair = Bip32ECKeyPair.deriveKeyPair(masterKeyPair, path)*//*
    *//*val bip44Keypair = Bip32ECKeyPair.deriveKeyPair(x, path)*//*
    *//*deriveKeyPair(x, path)*//*

    val x = ECKeyPair.create(BigInteger(privateKeyHex,16))

    val credentials = Credentials.create(x)
    val user = credentials.address


    println("Public Key: ${credentials.ecKeyPair.publicKey} Adress: $user ")
     }*/

