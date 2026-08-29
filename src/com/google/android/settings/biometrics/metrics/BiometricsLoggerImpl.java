package com.google.android.settings.biometrics.metrics;

import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.settings.biometrics.BiometricsOnboardingProto$FromSource;
import com.android.settings.biometrics.BiometricsOnboardingProto$Modality;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingAction;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingResult;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingScreen;
import com.android.settings.biometrics.BiometricsOnboardingProto$OnboardingScreenInfo;
import com.android.settings.biometrics.BiometricsOnboardingProto$RepeatedOnboardingScreenInfo;
import com.android.settings.biometrics.BiometricsOnboardingProto$RepeatedSettingsBiometricsOnboarding;
import com.android.settings.biometrics.BiometricsOnboardingProto$SettingsBiometricsOnboarding;
import com.android.settings.biometrics.metrics.BiometricsLogger;
import com.android.settings.biometrics.metrics.OnboardingEvent;
import com.android.settings.biometrics.metrics.OnboardingScreenInfoEvent;
import com.android.settings.core.instrumentation.SettingsStatsLog;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class BiometricsLoggerImpl implements BiometricsLogger {
    @Override // com.android.settings.biometrics.metrics.BiometricsLogger
    public void logSettingsBiometricsOnboarding(OnboardingEvent onboardingEvent) {
        ProtoOutputStream protoOutputStream = new ProtoOutputStream();
        Iterator it = onboardingEvent.getScreenInfos().iterator();
        while (it.hasNext()) {
            addScreenInfosToProto(protoOutputStream, (OnboardingScreenInfoEvent) it.next());
        }
        SettingsStatsLog.write(1060, onboardingEvent.getModality(), onboardingEvent.getFromSource(), onboardingEvent.getUserId(), onboardingEvent.getEnrolledCount(), onboardingEvent.getDuration(), onboardingEvent.getCapybaraStatus(), onboardingEvent.getResultCode(), onboardingEvent.getErrorCode(), protoOutputStream.getBytes());
    }

    private void addScreenInfosToProto(ProtoOutputStream protoOutputStream, OnboardingScreenInfoEvent onboardingScreenInfoEvent) {
        long jStart = protoOutputStream.start(2246267895809L);
        protoOutputStream.write(1159641169921L, onboardingScreenInfoEvent.getScreen());
        for (int i = 0; i < onboardingScreenInfoEvent.getActions().length; i++) {
            protoOutputStream.write(2259152797698L, onboardingScreenInfoEvent.getActions()[i]);
        }
        protoOutputStream.write(1112396529667L, onboardingScreenInfoEvent.getDuration());
        protoOutputStream.end(jStart);
    }

    @Override // com.android.settings.biometrics.metrics.BiometricsLogger
    public byte[] eventToMessageByteArray(OnboardingEvent onboardingEvent) {
        return eventToMessage(onboardingEvent).toByteArray();
    }

    private BiometricsOnboardingProto$SettingsBiometricsOnboarding eventToMessage(OnboardingEvent onboardingEvent) {
        ArrayList arrayList = new ArrayList();
        for (OnboardingScreenInfoEvent onboardingScreenInfoEvent : onboardingEvent.getScreenInfos()) {
            ArrayList arrayList2 = new ArrayList();
            for (int i : onboardingScreenInfoEvent.getActions()) {
                arrayList2.add(BiometricsOnboardingProto$OnboardingAction.forNumber(i));
            }
            arrayList.add((BiometricsOnboardingProto$OnboardingScreenInfo) BiometricsOnboardingProto$OnboardingScreenInfo.newBuilder().setOnboardingScreen(BiometricsOnboardingProto$OnboardingScreen.forNumber(onboardingScreenInfoEvent.getScreen())).setDwellTimeMillis(onboardingScreenInfoEvent.getDuration()).addAllOnboardingActions(arrayList2).build());
        }
        return (BiometricsOnboardingProto$SettingsBiometricsOnboarding) BiometricsOnboardingProto$SettingsBiometricsOnboarding.newBuilder().setModality(BiometricsOnboardingProto$Modality.forNumber(onboardingEvent.getModality())).setFromSource(BiometricsOnboardingProto$FromSource.forNumber(onboardingEvent.getFromSource())).setUser(onboardingEvent.getUserId()).setEnrolledCount(onboardingEvent.getEnrolledCount()).setDurationMillis(onboardingEvent.getDuration()).setCapybaraStatus(onboardingEvent.getCapybaraStatus()).setResultCode(BiometricsOnboardingProto$OnboardingResult.forNumber(onboardingEvent.getResultCode())).setErrorCode(onboardingEvent.getErrorCode()).setOnboardingScreenInfoList((BiometricsOnboardingProto$RepeatedOnboardingScreenInfo) BiometricsOnboardingProto$RepeatedOnboardingScreenInfo.newBuilder().addAllInfoList(arrayList).build()).build();
    }

    @Override // com.android.settings.biometrics.metrics.BiometricsLogger
    public OnboardingEvent messageByteArrayToEvent(byte[] bArr) {
        if (bArr != null && bArr.length != 0) {
            try {
                return new OnboardingEvent((BiometricsOnboardingProto$SettingsBiometricsOnboarding) ((BiometricsOnboardingProto$SettingsBiometricsOnboarding.Builder) BiometricsOnboardingProto$SettingsBiometricsOnboarding.newBuilder().mergeFrom(bArr)).build());
            } catch (InvalidProtocolBufferException e) {
                Log.w("BiometricsLogger", e.getMessage());
            }
        }
        return null;
    }

    @Override // com.android.settings.biometrics.metrics.BiometricsLogger
    public byte[] eventListToRepeatedMessageByteArray(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(eventToMessage((OnboardingEvent) it.next()));
        }
        return ((BiometricsOnboardingProto$RepeatedSettingsBiometricsOnboarding) BiometricsOnboardingProto$RepeatedSettingsBiometricsOnboarding.newBuilder().addAllRepeatedBiometricsOnboarding(arrayList).build()).toByteArray();
    }
}
