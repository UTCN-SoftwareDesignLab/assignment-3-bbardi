<template>
  <v-container>
    <v-card justify-center flat class="pa-4">
      <v-card-title>
        Users
        <v-spacer></v-spacer>
        <v-btn color="primary" dark @click="newUser"> New User </v-btn>
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
        :headers="userHeader"
        :items="users"
        :search="search"
        item-key="id"
        class="elevation-1"
      >
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editUser(item)">
            mdi-pencil
          </v-icon>
          <v-icon small @click="deleteUser(item)"> mdi-delete </v-icon>
        </template>
        <template v-slot:[`item.role`]="{ item }">
          {{parseRoles(item)}}
        </template>
      </v-data-table>
      <UserPrompt
        :openDialog="openDialog"
        :editedUser="selectedUser"
        @refresh="refreshList"
      >
      </UserPrompt>
    </v-card>
  </v-container>
</template>

<script>
import api from "../api";
import UserPrompt from "../components/UserPrompt"
export default {
  name: "UserAdmin",
  components:{UserPrompt},
  data: () => ({
    search: '',
    openDialog: false,
    selectedUser:{},
    users: [],
    userHeader: [
      {
        text: "Email",
        align: "start",
        value: "email",
      },
      {
        text: "UserName",
        value: "username",
      },
      {
        text: "Full Name",
        value: "fullName",
      },
      {
        text: "Roles",
        value: "role",
      },
      {
        text: "Actions",
        value: 'actions',
        sortable: false,
      },
    ],
  }),
  methods:{
    parseRoles(user){
      return api.users.parseRoles(user);
    },
    newUser(){
      this.selectedUser={};
      this.selectedUser.roles=[];
      this.openDialog = true;
    },
    editUser(user){
      user.password="";
      this.selectedUser = user;
      this.openDialog = true;
    },
    async deleteUser(user){
      await api.users.deleteUser(user)
      await this.refreshList();
    },
    async refreshList(){
      this.openDialog = false;
      this.users = await api.users.fetchAll();
    },
  },
  created(){
    this.refreshList();
  }
}
</script>