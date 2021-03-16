package service.dto;

public class RepairRecordRegistrationDto {

    private String repairRecordDescription;
    private Long detailPrice;
    private Long workPrice;
    private String otherNotes;
    private Long repairRequestId;

    private RepairRecordRegistrationDto(Builder builder) {
        this.repairRequestId = builder.repairRequestId;
        this.otherNotes = builder.otherNotes;
        this.workPrice = builder.workPrice;
        this.detailPrice = builder.detailPrice;
        this.repairRecordDescription = builder.repairRecordDescription;
    }


    public String getRepairRecordDescription() {
        return repairRecordDescription;
    }

    public Long getDetailPrice() {
        return detailPrice;
    }

    public Long getWorkPrice() {
        return workPrice;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public Long getRepairRequestId() {
        return repairRequestId;
    }

    public static class Builder {
        private String repairRecordDescription;
        private Long detailPrice;
        private Long workPrice;
        private String otherNotes;
        private Long repairRequestId;

        public Builder() {
        }


        public Builder setRepairRecordDescription(String repairRecordDescription) {
            this.repairRecordDescription = repairRecordDescription;
            return this;
        }

        public Builder setDetailPrice(Long detailPrice) {
            this.detailPrice = detailPrice;
            return this;
        }

        public Builder setWorkPrice(Long workPrice) {
            this.workPrice = workPrice;
            return this;
        }

        public Builder setOtherNotes(String otherNotes) {
            this.otherNotes = otherNotes;
            return this;
        }

        public Builder setRepairRequest(Long repairRequestId) {
            this.repairRequestId = repairRequestId;
            return this;
        }

        public RepairRecordRegistrationDto build() {
            return new RepairRecordRegistrationDto(this);

        }
    }
}
