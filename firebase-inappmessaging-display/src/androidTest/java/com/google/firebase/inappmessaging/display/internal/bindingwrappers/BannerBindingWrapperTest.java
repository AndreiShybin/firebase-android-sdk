// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class BannerBindingWrapperTest {
  private static final String IMAGE_URL = "https://www.imgur.com";
  private static final String CAMPAIGN_ID = "campaign_id";
  private static final String ACTION_URL = "https://www.google.com";
  private static final String CAMPAIGN_NAME = "campaign_name";
  private static final InAppMessage.Action ACTION =
      InAppMessage.Action.builder().setActionUrl(ACTION_URL).build();
  private static final InAppMessageLayoutConfig inappMessageLayoutConfig =
      InAppMessageLayoutConfig.builder()
          .setMaxDialogHeightPx((int) (0.8 * 1000))
          .setMaxDialogWidthPx((int) (0.7f * 1000))
          .setMaxImageHeightWeight(0.6f)
          .setMaxBodyHeightWeight(0.1f)
          .setMaxImageWidthWeight(0.9f) // entire dialog width
          .setMaxBodyWidthWeight(0.9f) // entire dialog width
          .setViewWindowGravity(Gravity.CENTER)
          .setWindowFlag(0)
          .setWindowWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
          .setWindowHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
          .setBackgroundEnabled(false)
          .setAnimate(false)
          .setAutoDismiss(false)
          .build();
  private static final InAppMessage BANNER_MESSAGE =
      InAppMessage.builder()
          .setCampaignId(CAMPAIGN_ID)
          .setIsTestMessage(false)
          .setCampaignName(CAMPAIGN_NAME)
          .setAction(ACTION)
          .setMessageType(MessageType.BANNER)
          .setImageUrl(IMAGE_URL)
          .build();
  @Rule public ActivityTestRule<TestActivity> rule = new ActivityTestRule<>(TestActivity.class);
  @Mock private View.OnClickListener onDismissListener;
  @Mock private View.OnClickListener actionListener;

  private BannerBindingWrapper bannerBindingWrapper;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    TestActivity testActivity = rule.getActivity();
    LayoutInflater layoutInflater =
        (LayoutInflater) testActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    bannerBindingWrapper =
        new BannerBindingWrapper(BANNER_MESSAGE, layoutInflater, inappMessageLayoutConfig);
  }

  @Test
  public void inflate_setsMessage() throws Exception {
    bannerBindingWrapper.inflate(actionListener, onDismissListener);

    assertEquals(bannerBindingWrapper.message, BANNER_MESSAGE);
  }

  @Test
  public void inflate_setsLayoutConfig() throws Exception {
    bannerBindingWrapper.inflate(actionListener, onDismissListener);

    assertEquals(bannerBindingWrapper.config, inappMessageLayoutConfig);
  }

  @Test
  public void inflate_setsActionListener() throws Exception {
    bannerBindingWrapper.inflate(actionListener, onDismissListener);

    assertTrue(bannerBindingWrapper.getDialogView().hasOnClickListeners());
  }
}
