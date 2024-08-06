export interface Post {
  id: string;
  title: string;
  content: string;
  author: any;
}
export interface Comment {
  id: string;
  content: string;
  author: any;
}

export interface User {
  id: string;
  name: string;
  email: string;
}
