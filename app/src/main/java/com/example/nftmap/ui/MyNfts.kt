package com.example.nftmap.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nftmap.nftwallet.NftsOwned
import com.example.nftmap.nftwallet.UserNftsService
import com.walletconnect.web3.modal.client.Web3Modal
import com.walletconnect.web3.modal.ui.components.internal.Web3ModalComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun MyNfts() {
    val allNftsOwned = remember { mutableStateListOf<NftsOwned>() }
    val context = LocalContext.current
    var showBottomSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        val w = Web3Modal.getAccount()


       if(w == null){
           if(showBottomSheet){
               ModalBottomSheet(
                   sheetState = sheetState,
                   onDismissRequest = {   }
               ) {
                   // Sheet content
                   Web3ModalComponent(
                       shouldOpenChooseNetwork = false ,
                       closeModal = { scope.launch {
                           showBottomSheet = false
                           sheetState.hide()
                       }
                       }
                   )
               }
           }
       } else {
           LaunchedEffect( key1 = "hallo", block =
           {
               withContext(Dispatchers.IO) {
                   val nftService = UserNftsService.create()
                   nftService.fillNfts(w.address).forEach {
                       allNftsOwned.add(it)
                   }
               }
           }
           )

           LazyVerticalGrid(
               modifier = Modifier
                   .padding(vertical = 4.dp),
                    columns = GridCells.Fixed(2),
               verticalArrangement = Arrangement.spacedBy(4.dp),
               horizontalArrangement = Arrangement.spacedBy(8.dp)
           ) {
            items(items=allNftsOwned){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dp(LocalConfiguration.current.screenHeightDp.toFloat() / 6)),
            ) {
                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center){
                GlideImage(
                    model = it.source,
                    contentDescription = it.name
                )
                }
            }
            }
           }
       }
       }
}
@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEvent: (LifecycleOwner, Lifecycle.Event) -> Unit
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            onEvent(source, event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}