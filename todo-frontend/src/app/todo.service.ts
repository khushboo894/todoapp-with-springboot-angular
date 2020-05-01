import { Injectable } from '@angular/core';
import { Todo } from './todo';
import {HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class TodoService {
  private baseUrl = 'http://localhost:8080/todoapi/todos/';

  constructor(private http: HttpClient) { }

  getTodos() : Promise<Todo[]>{
    return this.http.get(this.baseUrl)
    .toPromise().then(response => response as Todo[])
    .catch(this.handleError);   
  }

  createTodo(todoData: Todo): Promise<Todo> {
    return this.http.post(this.baseUrl, todoData)
      .toPromise().then(response => response as Todo)
      .catch(this.handleError);
  }

  updateTodo(todoData: Todo): Promise<Todo> {
    return this.http.put(this.baseUrl + todoData.id, todoData)
      .toPromise()
      .then(response => response as Todo)
      .catch(this.handleError);
  }

  deleteTodo(id: string): Promise<any> {
    return this.http.delete(this.baseUrl + id)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('Some error occured', error);
    return Promise.reject(error.message || error);
  }
}