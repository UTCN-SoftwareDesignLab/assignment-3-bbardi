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
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="editedConsult.patientName"
                  label="Patient"
                  solo
                  :items="patients"
                  :rules="[rules.required]"
                ></v-autocomplete>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="editedConsult.doctorName"
                  label="Doctor"
                  solo
                  :items="doctors"
                  :rules="[rules.required]"
                ></v-autocomplete>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-date-picker
                  v-model="editedConsult.date"
                  @click:date="setSlots"
                >
                </v-date-picker>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-autocomplete
                  v-model="editedConsult.time"
                  label="Timeslot"
                  solo
                  no-filter
                  :items="slots"
                  :rules="[rules.required]"
                ></v-autocomplete>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="24" sm="12" md="8">
                <v-text-field
                  v-model="editedConsult.description"
                  label="Description"
                  :rules="[rules.required]"
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
  name: "ConsultationPrompt",
  props: {
    openDialog: Boolean,
    editedConsult: Object,
    patients: Array,
    doctors: Array,
  },
  data: () => ({
    slots:[],
    rules: {
      required: (value) => !!value || "Required.",
      email: (value) => /\S+@\S+\.\S+/.test(value) || "Must be a valid email.",
      selectedone: (value) =>
        (!!value && value.length > 0) || "At least one must be selected.",
      name: (value) =>
        /^[A-Z][a-z]+\s[A-Z][a-z]+$/.test(value) || "Must be a valid name.",
    },
    valid: true,
  }),
  computed: {
    formTitle() {
      return !this.editedConsult.id ? "New Consult" : "Edit Consult";
    },
  },
  methods: {
    close() {
      this.$emit("refresh");
    },
    save() {
      if (!this.editedConsult.id) {
        api.consultations.createConsult(this.editedConsult).then(() => this.$emit("refresh"));
      } else {
        api.consultations.editConsult(this.editedConsult).then(() => this.$emit("refresh"));
      }
    },
    async setSlots() {
        this.slots = await api.planning.fetchFreeTimeSlots(this.editedConsult.doctorName,this.editedConsult.date);
        if(this.slots.length == 0)
            this.valid = false;
    },
  },
};
</script>