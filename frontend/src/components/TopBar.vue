<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-toolbar-title>Clinic</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-sm-and-down">
        <v-btn v-show="isSecretary" @click="goToPatient">Patients</v-btn>
        <v-btn v-show="isAdmin" @click="goToUsers">Users</v-btn>
        <v-btn v-show="isDoctor" @click="goToConsult">Consultations</v-btn>
        <v-btn v-show="isDoctor" @click="togglePager">{{pagerStatus}}</v-btn>
      </v-toolbar-items>
      <v-spacer></v-spacer>
      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import router from "../router";
export default {
  name: "TopBar",
  data: ()=>({
    socket: null,
    stompClient: null,
    connected: false,
  }),
  methods: {
    goToPatient(){
      router.push("/patients");
    },
    goToUsers(){
      router.push("/users");
    },
    goToConsult(){
      router.push("/consult");
    },
    togglePager(){
      if(!this.connected){
      this.socket = new SockJS("http://localhost:8080/api/clinic/notifications");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame =>{
          this.connected = true;
          var username = this.$store.getters["auth/getUsername"];
          this.stompClient.subscribe(`/user/${username}/message`, tick =>{
            console.log(tick);
            alert(JSON.parse(tick.body).message);
          })
        }
      )
      }
      else{
        this.connected = false
        this.stompClient.disconnect();
      }
    },
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
  },
  computed: {
    isAdmin: function (){
      return this.$store.getters["auth/isAdmin"]
    },
    isSecretary: function (){
      return this.$store.getters["auth/isSecretary"]
    },
    isDoctor: function (){
      return this.$store.getters["auth/isDoctor"]
    },
    pagerStatus: function(){
      if(this.connected){
        return "Pager off";
      }
      else
        return "Pager on";
    },
  }
};
</script>

<style scoped></style>