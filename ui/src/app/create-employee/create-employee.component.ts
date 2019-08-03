import { EmployeeService } from './../employee.service';
import { Employee } from './../employee';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent implements OnInit {
  registerForm: FormGroup;
  submitted = false;

  constructor(private employeeService: EmployeeService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern("^[2-9][0-9]\\d\\-?[2-9]([02-9]{2}|[02-9]\\d||\\d[02-9])\\-?\\d{4}$")]]
    });
  }

  get form() { return this.registerForm.controls; }

  newEmployee(): void {
    this.submitted = false;
  }

  save() {
    this.employeeService.createEmployee(this.registerForm.value)
        .subscribe(data => console.log(data), error => console.log(error));
  }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    this.save();
  }
}