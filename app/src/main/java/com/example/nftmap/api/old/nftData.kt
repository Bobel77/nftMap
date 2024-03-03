package com.example.nftmap.api.old


import kotlinx.serialization.Serializable
@Serializable
data class OwnedNftsResponse(
    val ownedNfts: List<NftObject>?,
    val totalCount: Int?,
    val validAt: ValidAtObject?,
    val pageKey: String?
)

@Serializable
data class NftObject(
    val contract: ContractObject?,
    val tokenId: String?,
    val tokenType: String?,
    val name: String?,
    val description: String?,
    val image: ImageObject?,
    val raw: RawObject?,
    val tokenUri: String?,
    val timeLastUpdated: String?,
    val balance: String?
)

@Serializable
data class ContractObject(
    val address: String?,
    val name: String?,
    val symbol: String?,
    val totalSupply: String?,
    val tokenType: String?,
    val contractDeployer: String?,
    val deployedBlockNumber: Int?,
    val openSeaMetadata: OpenSeaMetadataObject?,
    val isSpam: Boolean?,
    val spamClassifications: List<String>?
)

@Serializable
data class OpenSeaMetadataObject(
    val floorPrice: Double?,
    val collectionName: String?,
    val safelistRequestStatus: String?,
    val imageUrl: String?,
    val description: String?,
    val externalUrl: String?,
    val twitterUsername: String?,
    val discordUrl: String?,
    val lastIngestedAt: String?
)

@Serializable
data class ImageObject(
    val cachedUrl: String?,
    val thumbnailUrl: String?,
    val pngUrl: String?,
    val contentType: String?,
    val size: Int?,
    val originalUrl: String?
)

@Serializable
data class RawObject(
    val tokenUri: String?,
    val metadata: MetadataObject?,
    val error: String?
)

@Serializable
data class MetadataObject(
    val name: String?,
    val description: String?,
    val image: String?,
    val external_url: String?,
    val attributes: List<AttributeObject>?
)

@Serializable
data class AttributeObject(
    val value: String?,
    val trait_type: String?
)

@Serializable
data class ValidAtObject(
    val blockNumber: Int?,
    val blockHash: String?,
    val blockTimestamp: String?
)