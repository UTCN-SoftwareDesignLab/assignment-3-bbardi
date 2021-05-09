<template>
  <v-container>
    <v-card justify-center flat class="pa-4">
      <v-card-title>
        Patients
        <v-spacer></v-spacer>
        <v-btn color="primary" dark @click="newPatient"> New Patient </v-btn>
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
        :headers="patientHeader"
        :items="patients"
        :search="search"
        item-key="id"
        class="elevation-1"
      >
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editPatient(item)">
            mdi-pencil
          </v-icon>
        </template>
      </v-data-table>
      <PatientPrompt
        :openDialog="openDialog"
        :editedPatient="selectedPatient"
        @refresh="refreshList"
      >
      </PatientPrompt>
    </v-card>
  </v-container>
</template>

<script>
import api from "../api";
import PatientPrompt from "../components/PatientPrompt"
export default {
  name: "PatientsManagement",
  components:{PatientPrompt},
  data: () => ({
    search: '',
    openDialog: false,
    selectedPatient:{},
    patients: [],
    patientHeader: [
      {
        text: "Name",
        align: "start",
        value: "name",
      },
      {
        text: "ID Card",
        value: "idCard",
      },
      {
        text: "CNP",
        value: "personalNumericCode",
      },
      {
        text: "Address",
        value: "address",
      },
      {
        text: "Date of Birth",
        value: "dateOfBirth",
      },
      {
        text: "Actions",
        value: 'actions',
        sortable: false,
      },
    ],
  }),
  methods:{
    newPatient(){
      this.selectedPatient={};
      this.openDialog = true;
    },
    editPatient(patient){
      this.selectedPatient = patient;
      this.openDialog = true;
    },
    async refreshList(){
      this.openDialog = false;
      this.patients = await api.patients.fetchAll();
    },
  },
  created(){
    this.refreshList();
  }
}
</script>