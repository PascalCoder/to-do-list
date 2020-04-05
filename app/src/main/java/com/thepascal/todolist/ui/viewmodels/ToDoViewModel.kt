package com.thepascal.todolist.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.thepascal.todolist.POSITION_NOT_SET
import com.thepascal.todolist.model.AddressModel
import com.thepascal.todolist.model.ToDoModel

class ToDoViewModel: ViewModel() {

    /*var _type: LiveData<String> = MutableLiveData<String>().apply { value = ""}
    var _title: LiveData<String> = MutableLiveData<String>().apply { value = ""}
    var _description: LiveData<String?> = MutableLiveData<String?>().apply { value = null}
    var _completed: LiveData<Boolean?> = MutableLiveData<Boolean?>().apply { value = false}
    var _datePosted: LiveData<String> = MutableLiveData<String>().apply { value = ""}
    var _dueDate: LiveData<String?> = MutableLiveData<String?>().apply { value = null}
    var _dueTime: LiveData<String?> = MutableLiveData<String?>().apply { value = null}*/

    /*private fun <T> LiveData(formattedDate: T, function: () -> Unit): LiveData<String?> {

    }*/
    var isViewModelNew = true
    var dueDate: String? = null
    var dueTime: String? = null
    var address: AddressModel? = null

    var isAddressContentDisplayed: Boolean = false
    var taskPosition = POSITION_NOT_SET

    var toDoTask: ToDoModel? = null
}