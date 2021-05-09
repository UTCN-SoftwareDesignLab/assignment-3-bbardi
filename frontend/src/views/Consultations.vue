<template>
  <v-container>
    <v-card justify-center flat class="pa-4">
      <v-card-title>
        Consultations
        <v-spacer></v-spacer>
        <v-btn color="primary" v-show="isSecretary" dark @click="newConsult"> Schedule Consultation </v-btn>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <!--<v-toolbar dense floating>
        <v-text-field prepend-icon="mdi-magnify" hide-details single-line>
        </v-text-field>
      </v-toolbar>-->

      <v-data-table
        :headers="consultHeader"
        :items="consultations"
        :search="search"
        item-key="id"
        show-expand
        class="elevation-1"
      >
        <template v-slot:expanded-item="{ headers, item }">
          <td :colspan="headers.length">
            {{ item.description }}
          </td>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editConsult(item)">
            mdi-pencil
          </v-icon>
          <v-icon small v-show="isSecretary" class="mr-2" @click="deleteConsult(item)">
            mdi-delete
          </v-icon>
          <v-icon small v-show="isSecretary" class="mr-2" @click="checkin(item)">
            mdi-check
          </v-icon>
        </template>
      </v-data-table>
      <ConsultationPrompt
        :openDialog="openDialog"
        :editedConsult="selectedConsult"
        :patients="patientsList"
        :doctors="doctorsList"
        @refresh="refreshList"
      >
      </ConsultationPrompt>
    </v-card>
  </v-container>
</template>

<script>
import api from "../api";
import ConsultationPrompt from "../components/ConsultationPrompt";

export default {
  name: "Consultations",
  components:{ConsultationPrompt},
  data: () => ({
    search: '',
    openDialog: false,
    selectedConsult:{},
    consultations: [],
    patientsList: [],
    doctorsList: [],
    currentSlot: null,
    consultHeader: [
      {
        text: "Patient Name",
        align: "start",
        value: "patientName",
      },
      {
        text: "Date",
        value: "date",
      },
      {
        text: "Time",
        value: "time",
      },
      {
        text: "Doctor Name",
        value: "doctorName",
      },
      {
        text: "Actions",
        value: 'actions',
        sortable: false,
      },
      {
        text: "",
        value: "data-table-expand",
      },
    ],
  }),
  methods:{
    newConsult(){
      this.selectedConsult = {};
      this.openDialog = true;
    },
    async editConsult(consult){
      this.selectedConsult = consult;
      this.openDialog = true;
    },
    async deleteConsult(consult){
      api.consultations.deleteConsult(consult);
      await this.refreshList();
    },
    checkin(consult){
      api.consultations.checkin(consult);
    },
    async refreshList(){
      this.openDialog = false;
      this.consultations = await api.consultations.fetchAll();
      this.doctorsList = await api.planning.fetchDoctors();
      this.patientsList = await api.patients.fetchNames();
    },
  },
  created(){
    this.refreshList();
  },
  computed:{
    isSecretary: function (){
      return this.$store.getters["auth/isSecretary"]
    },
  }
}
</script>