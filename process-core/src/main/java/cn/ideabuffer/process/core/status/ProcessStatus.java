package cn.ideabuffer.process.core.status;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author sangjian.sj
 * @date 2020/03/24
 */
public class ProcessStatus implements Serializable {

    public static final ProcessStatus PROCEED = new InnerProcessStatus(true) {
        private static final long serialVersionUID = -246614296436859585L;
    };
    public static final ProcessStatus COMPLETE = new InnerProcessStatus(false) {
        private static final long serialVersionUID = -2356048364637594820L;
    };
    private static final long serialVersionUID = 8817242079278554141L;
    private boolean proceed;
    private ProcessErrorCode errorCode;

    public ProcessStatus(boolean proceed) {
        this.proceed = proceed;
    }

    public ProcessStatus(ProcessErrorCode errorCode) {
        this.proceed = false;
        this.errorCode = errorCode;
    }

    public static ProcessStatus proceed() {
        return new ProcessStatus(true);
    }

    public static ProcessStatus completeWithError() {
        return new ProcessStatus(false);
    }

    public static ProcessStatus create(boolean proceed) {
        return new ProcessStatus(proceed);
    }

    public static ProcessStatus completeWithError(String code, String message) {
        return completeWithError(new ProcessErrorCode(code, message));
    }

    /**
     * @param errorCode
     * @return
     */
    public static ProcessStatus completeWithError(ProcessErrorCode errorCode) {
        return new ProcessStatus(errorCode);
    }

    /**
     * 返回失败结果，data为null
     *
     * @param errorCodeProvider
     * @return
     */
    public static ProcessStatus completeWithError(@NotNull ProcessErrorCodeProvider errorCodeProvider) {
        return completeWithError(errorCodeProvider.getErrorCode());
    }

    public static boolean isComplete(ProcessStatus status) {
        return status == null || status.isComplete();
    }

    /**
     * result不为null，并且success为true
     *
     * @param status
     * @return
     */
    public static boolean isProceed(ProcessStatus status) {
        return status != null && status.isProceed();
    }

    public static boolean isSuccess(ProcessStatus status) {
        return isProceed(status);
    }

    public static boolean isFailure(ProcessStatus status) {
        return isComplete(status);
    }

    public boolean isSuccess() {
        return isProceed();
    }

    public boolean isFailure() {
        return isComplete();
    }

    public boolean isProceed() {
        return this.proceed;
    }

    public boolean isComplete() {
        return !isProceed();
    }

    public void setErrorCode(String code, String message) {
        setErrorCode(new ProcessErrorCode(code, message));
    }

    public ProcessErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ProcessErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getCode() {
        return errorCode == null ? null : errorCode.getCode();
    }

    public String getMessage() {
        return errorCode == null ? null : errorCode.getMessage();
    }

    @Override
    public String toString() {
        return "ProcessStatus{" +
            "proceed=" + proceed +
            ", errorCode=" + errorCode +
            '}';
    }

    private static class InnerProcessStatus extends ProcessStatus {
        private static final long serialVersionUID = -9004945559952984969L;

        InnerProcessStatus(boolean proceed) {
            super(proceed);
        }

        @Override
        public void setErrorCode(String code, String message) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ProcessErrorCode getErrorCode() {
            return null;
        }

        @Override
        public void setErrorCode(ProcessErrorCode errorCode) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getCode() {
            return null;
        }

        @Override
        public String getMessage() {
            return null;
        }

    }
}
