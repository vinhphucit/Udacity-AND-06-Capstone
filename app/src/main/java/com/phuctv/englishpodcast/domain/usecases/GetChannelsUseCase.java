package com.phuctv.englishpodcast.domain.usecases;

import com.phuctv.englishpodcast.domain.models.ChannelModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by phuctran on 11/7/17.
 */

public class GetChannelsUseCase {
    public Single<List<ChannelModel>> getChannelList() {
        return Observable.fromCallable(() -> {
            List<ChannelModel> channelModels = new ArrayList<>();
            channelModels.add(new ChannelModel("0", "6 Minute English ", "Our long-running series of topical discussion and new vocabulary, brought to you by your favourite BBC Learning English presenters.", "http://ichef.bbci.co.uk/images/ic/1920xn/p05lvkg1.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/0/2017"));
            channelModels.add(new ChannelModel("1", "English at Work", "English at Work", "http://regionalconnections.tpn.miupdate.com/asset_library/page/cfmr/m_federalskilled.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/1/2017"));
            channelModels.add(new ChannelModel("2", "Express English", "Every week we ask you a different question. Hear what people in London say, then join the conversation!", "http://pic.pimg.tw/elitelovebbc/1356674626-3304222869.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/2/2015"));
            channelModels.add(new ChannelModel("4", "The English We Speak", "The English We Speak is your chance to catch up on the very latest English words and phrases.", "http://ichef.bbci.co.uk/images/ic/1248xn/p05bsf1r.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/4/2017"));
            channelModels.add(new ChannelModel("5", "Dramas", "Dramas", "http://ichef.bbci.co.uk/images/ic/640x360/p02bdq2j.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/5/2017"));
            channelModels.add(new ChannelModel("6", "News Report", "Improve your listening skills with News Report - our English language teaching series that uses authentic audio news stories from the BBC.", "http://ichef.bbci.co.uk/images/ic/1248xn/p03y94zy.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/6/2016"));
            channelModels.add(new ChannelModel("7", "Lingohack", "Get up-to-date with the latest news and understand it too with Lingohack.", "http://ichef.bbci.co.uk/images/ic/640x360/p042jh2y.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/7/2017"));
            channelModels.add(new ChannelModel("8", "The Sounds of English", "The Sounds of English", "http://ichef.bbci.co.uk/images/ic/960xn/p04nc85l.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/8/2017"));
            channelModels.add(new ChannelModel("9", "Grammar", "English Grammar In Use", "http://ichef.bbci.co.uk/bbcle/images/width/live/p0/2p/kl/p02pklc6.jpg/624", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/9/2017"));
            channelModels.add(new ChannelModel("10", "Words in the News", "Words in the News", "https://blog.oxforddictionaries.com/wp-content/uploads/bangarang-e1455123384347.jpg", "https://avid-heading-737.appspot.com/_ah/api/bbc_english/v1/category/songsfull/10/2017"));
            return channelModels;
        }).flatMap(Observable::fromIterable)
                .toList();
    }
}
