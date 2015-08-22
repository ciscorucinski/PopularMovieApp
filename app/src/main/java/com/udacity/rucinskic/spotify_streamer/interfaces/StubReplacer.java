package com.udacity.rucinskic.spotify_streamer.interfaces;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface StubReplacer<VG extends ViewGroup> {

	VG convertStubIntoViewGroup();

	View createNewInitializedView(Context context, VG layout);

}