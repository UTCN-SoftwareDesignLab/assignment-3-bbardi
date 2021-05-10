<template>
  <v-dialog persistent :value="openDialog" max-width="500px">
    <v-form lazy-validation v-model="valid" ref="form">
      <v-card>
        <v-card-title>
          <span class="headline">{{ formTitle }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedPatient.name"
                  label="Patient Name"
                  :rules="[rules.required, rules.name]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedPatient.idCard"
                  label="Identity Card"
                  :rules="[rules.required, rules.idCard]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedPatient.personalNumericCode"
                  label="CNP"
                  :rules="[rules.required, rules.cnp]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedPatient.address"
                  label="Address"
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6" md="4">
                <v-text-field
                  v-model="editedPatient.dateOfBirth"
                  label="Date Of Birth"
                  :rules="[rules.required, rules.dob]"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="close"> Cancel </v-btn>
          <v-btn color="blue darken-1" text @click="save" :disabled="!valid">
            Save
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-form>
  </v-dialog>
</template>

<script>
import api from "../api";
export default {
  name: "PatientPrompt",
  props: {
    openDialog: Boolean,
    editedPatient: Object,
  },
  data: () => ({
    rules: {
      required: (value) => !!value || "Required.",
      name: (value) => /^[A-Z][a-z]+\s[A-Z][a-z]+$/.test(value) || "Must be a valid name.",
      dob: (value) => /^\d\d\d\d-\d\d-\d\d$/.test(value) || "Must be a valid date(yyyy-MM-dd)",
      idCard: (value) => /^[A-Z][A-Z]\d{6}$/.test(value) || "Must be a valid ID card",
      cnp: (value) => /^\d{13}$/.test(value) || "Must be a valid Personal Numeric Code",
    },
    valid: true,
  }),
  computed: {
    isRequired() {
      return !this.editedPatient.id ? [this.rules.required] : [];
    },
    formTitle() {
      return !this.editedPatient.id ? "New Patient" : "Edit Patient";
    },
  },
  methods: {
    close() {
      this.$emit("refresh");
    },
    save() {
      if (!this.editedPatient.id) {
        api.patients.createPatient(this.editedPatient).then(() => this.$emit("refresh"));
      } else {
        api.patients.editPatient(this.editedPatient).then(() => this.$emit("refresh"));
      }
    },
  },
};
</script>