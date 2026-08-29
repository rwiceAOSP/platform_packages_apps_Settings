package com.google.android.settings.biometrics.face.anim.curve;

import android.graphics.Path;

/* JADX INFO: loaded from: classes4.dex */
public class CellConfig {
    final boolean mFlipVertical;
    final Path mPath;
    final int mRotation;

    CellConfig(Path path, int i) {
        this(path, i, false);
    }

    CellConfig(Path path, int i, boolean z) {
        this.mPath = path;
        this.mRotation = i;
        this.mFlipVertical = z;
    }
}
