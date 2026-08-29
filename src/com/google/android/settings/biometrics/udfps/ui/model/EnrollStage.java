package com.google.android.settings.biometrics.udfps.ui.model;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 com.google.android.settings.biometrics.udfps.ui.model.EnrollStage, still in use, count: 1, list:
  (r0v0 com.google.android.settings.biometrics.udfps.ui.model.EnrollStage) from 0x0057: SPUT (r0v0 com.google.android.settings.biometrics.udfps.ui.model.EnrollStage) (LINE:29) com.google.android.settings.biometrics.udfps.ui.model.EnrollStage.INIT_STAGE com.google.android.settings.biometrics.udfps.ui.model.EnrollStage
	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:101)
	at java.base/java.util.ArrayList.forEach(ArrayList.java:1612)
	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:100)
	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:257)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:187)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: EnrollStage.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class EnrollStage {
    UNKNOWN(-1),
    CENTER(0),
    GUIDED(1),
    FINGERTIP(2),
    LEFT_EDGE(3),
    RIGHT_EDGE(4);

    private static final /* synthetic */ EnumEntries $ENTRIES;
    public static final Companion Companion;
    private static final EnrollStage INIT_STAGE;
    private static final EnrollStage LAST_STAGE;
    private static final EnrollStage[] POSITIVE_STAGES;
    private final int value;

    public static EnrollStage valueOf(String str) {
        return (EnrollStage) Enum.valueOf(EnrollStage.class, str);
    }

    public static EnrollStage[] values() {
        return (EnrollStage[]) $VALUES.clone();
    }

    private EnrollStage(int i) {
        super(str, i);
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }

    static {
        EnrollStage enrollStage = RIGHT_EDGE;
        EnrollStage[] enrollStageArrValues = values();
        $ENTRIES = EnumEntriesKt.enumEntries(enrollStageArrValues);
        Companion = new Companion(null);
        POSITIVE_STAGES = new EnrollStage[]{enrollStage, enrollStage, enrollStage, enrollStage, enrollStage};
        INIT_STAGE = enrollStage;
        LAST_STAGE = enrollStage;
    }

    /* JADX INFO: compiled from: EnrollStage.kt */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final EnrollStage[] getPOSITIVE_STAGES() {
            return EnrollStage.POSITIVE_STAGES;
        }

        public final EnrollStage getINIT_STAGE() {
            return EnrollStage.INIT_STAGE;
        }

        public final EnrollStage getLAST_STAGE() {
            return EnrollStage.LAST_STAGE;
        }
    }
}
