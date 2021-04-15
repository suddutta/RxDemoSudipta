package com.sudipta.rxdemo

class DataSource {
    companion object{
        fun  createEmployeeList() : MutableList<Employee> {
            var list = mutableListOf<Employee>()
            list.add(Employee("Rahul","He is App Developer",true))
            list.add(Employee("Ranjan","He is Web Developer",true))
            list.add(Employee("Shaun","He is iOS Developer",false))
            list.add(Employee("Ali","He is flutter Developer",true))
            list.add(Employee("Krishna","He is ionic Developer",true))
            return  list
        }

        fun createMaleUserList():MutableList<User>{
            var list = mutableListOf<User>()
            list.add(User("Rajendra","Male"))
            list.add(User("Mir","Male"))
            list.add(User("Avijit","Male"))
            return  list

        }


        fun createFeMaleUserList():MutableList<User>{
            var list = mutableListOf<User>()
            list.add(User("Sohini","Female"))
            list.add(User("Smita","Female"))
            list.add(User("Moitri","Female"))
            return  list

        }
    }
}