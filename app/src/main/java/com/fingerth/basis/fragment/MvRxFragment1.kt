package com.fingerth.basis.fragment

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.*
import com.fingerth.basis.base.BaseEpoxyFragment
import com.fingerth.basis.views.marquee

//data class ShowcaseState(val response: Async<AnimationResponseV2> = Uninitialized) : MvRxState
data class ShowTvState(val response: String = "Uninitialized") : MvRxState


open class MvRxViewModel<S : MvRxState>(initialState: S) : BaseMvRxViewModel<S>(initialState, BuildConfig.DEBUG)
class ShowcaseViewModel(initialState: ShowTvState) : MvRxViewModel<ShowTvState>(initialState) {
    init {
        //                api.getCollection()
        //                    .subscribeOn(Schedulers.io())
        //                    .retry(3)
        //                    .execute { copy(response = it) }
    }


    companion object : MvRxViewModelFactory<ShowcaseViewModel, ShowTvState> {
        var service = "00"

        override fun create(viewModelContext: ViewModelContext, state: ShowTvState): ShowcaseViewModel? {

            //            Thread {
            //                Thread.sleep(5000)
            //state.copy(Async<String>("xx") )
            //            }.start()

            return ShowcaseViewModel(state.copy(response = service))
        }
        //        override fun create(viewModelContext: ViewModelContext, state: ShowcaseState): ShowcaseViewModel? {
        //            val service = viewModelContext.app<LottieApplication>().lottiefilesService
        //            return ShowcaseViewModel(state, service)
        //        }

        fun xx() {
            service = "111111"
        }
    }
}

class MvRxFragment1 : BaseEpoxyFragment() {
    init {
        Thread {
            Thread.sleep(15000)
        }.start()
    }

    private val viewModel: ShowcaseViewModel by fragmentViewModel()

    override fun EpoxyController.buildModels() = withState(viewModel) { state ->
        marquee {
            id("showcase")
            title("Showcase : " + state.response)
        }
    }

    //     fun EpoxyController.buildModels() = withState(viewModel) { state ->
    //        marquee {
    //            id("showcase")
    //            title("Showcase")
    //        }
    //        showcaseCarousel {
    //            id("carousel")
    //            showcaseItems(showcaseItems)
    //        }
    //
    //        val collectionItems = state.response()?.data
    //
    //        if (collectionItems == null) {
    //            loadingView {
    //                id("loading")
    //            }
    //        } else {
    //            collectionItems.forEach {
    //                animationItemView {
    //                    id(it.id)
    //                    title(it.title)
    //                    previewUrl("https://assets9.lottiefiles.com/${it.preview}")
    //                    previewBackgroundColor(it.bgColorInt)
    //                    onClickListener { _ -> startActivity(PlayerActivity.intent(requireContext(), CompositionArgs(animationDataV2 = it))) }
    //                }
    //            }
    //        }
    //    }
}


private class BaseEpoxyController(private val buildModelsCallback: EpoxyController.() -> Unit) : AsyncEpoxyController() {
    override fun buildModels() {
        buildModelsCallback()
    }

}