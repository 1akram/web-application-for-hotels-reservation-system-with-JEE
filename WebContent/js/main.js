// search 
function search() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("userTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[0];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }       
    }
}
//   end search  
// edit 

function editType(id){
  var tempId,tr,form;
  tempId="i"+id;
  tr=document.getElementById(tempId);
  document.getElementById("deleteType").href="?op=delete&id="+id;
  form=document.getElementById("editModel").getElementsByTagName("form")[0];
  form.getElementsByClassName("id")[0].value=id;
  form.getElementsByClassName("name")[0].value=tr.getElementsByTagName("td")[0].textContent;
  form.getElementsByClassName("price")[0].value=tr.getElementsByTagName("td")[1].textContent;
  form.getElementsByClassName("guests")[0].value=tr.getElementsByTagName("td")[2].textContent;
  form.getElementsByClassName("description")[0].value=tr.getElementsByTagName("td")[3].textContent;
  form.getElementsByClassName("id")[0].value=id;
  var checkboxlist=form.getElementsByClassName("checkboxList")[0].getElementsByTagName("input");
  var _checkboxList=tr.getElementsByTagName("td")[4].getElementsByTagName("span");
  for(var i=0;i<checkboxlist.length;i++){
    
    for(var j=0;j<_checkboxList.length;j++){
      if(checkboxlist[i].value===_checkboxList[j].textContent){
        checkboxlist[i].checked=true;
        break;
      }
      checkboxlist[i].checked=false;
    }
  }



  $("#editModel").modal();
  return;
}

//------------------
//------------------
function editUser(id){
  var tempId,tr, model,form;
  tempId="i"+id;
  tr=document.getElementById(tempId);
  document.getElementById("deleteUser").href="?op=delete&id="+id;
  model=document.getElementById("editModel");
  form=model.getElementsByTagName("form")[0];
  form.getElementsByClassName("firstName")[0].value=tr.getElementsByTagName("td")[0].textContent;
  form.getElementsByClassName("lastName")[0].value=tr.getElementsByTagName("td")[1].textContent;
  form.getElementsByClassName("email")[0].value=tr.getElementsByTagName("td")[2].textContent;
  form.getElementsByClassName("dateOfBirth")[0].value=tr.getElementsByTagName("td")[3].textContent;
  form.getElementsByClassName("gender")[0].value=tr.getElementsByTagName("td")[4].textContent;
  form.getElementsByClassName("street")[0].value=tr.getElementsByTagName("td")[5].textContent;
  form.getElementsByClassName("city")[0].value=tr.getElementsByTagName("td")[6].textContent;
  form.getElementsByClassName("state")[0].value=tr.getElementsByTagName("td")[7].textContent;
  form.getElementsByClassName("zipCode")[0].value=tr.getElementsByTagName("td")[8].textContent;
  form.getElementsByClassName("phoneN")[0].value=tr.getElementsByTagName("td")[9].textContent;
  form.getElementsByClassName("id")[0].value=id;
  $("#editModel").modal();
  return;
}
//-----------
//----------
function editRoom(id){
  var tempId,tr, model,form;
  tempId="i"+id;
  tr=document.getElementById(tempId);
  model=document.getElementById("editModel");
  form=model.getElementsByTagName("form")[0];
  form.getElementsByClassName("roomNumber")[0].value=tr.getElementsByTagName("td")[0].textContent;
  form.getElementsByClassName("type")[0].value=tr.getElementsByTagName("td")[1].id;
  form.getElementsByClassName("floor")[0].value=tr.getElementsByTagName("td")[2].id;
  document.getElementById("deleteRoom").href="?op=delete&id="+id;
  form.getElementsByClassName("id")[0].value=id;
  
  $("#editModel").modal();
  return;
}
// end edit 
// edit 

function upload(id){
  document.getElementById("uploadModel").getElementsByTagName("form")[0].action="upload?op=img&id="+id;
  $("#uploadModel").modal();
  return;
}