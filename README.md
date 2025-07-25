# Home Inventory Management System

A responsive web application for managing household inventory items with role-based access control. <br />
Built with Java Servlet, JSP, JPA, and MySQL.

가정 내 물품을 효율적으로 관리할 수 있는 반응형 웹 애플리케이션입니다. <br />
사용자 역할 기반으로 접근 권한이 제어되며, Java Servlet, JSP, JPA, MySQL을 활용하여 개발되었습니다.

## 💡 Overview

Users can manage personal or shared household items through a role-based system. <br />
Admins can oversee family units and system-wide data, while each user maintains their own inventory.

사용자는 개인 또는 공동 가정용 물품을 역할 기반 시스템을 통해 관리할 수 있으며, 관리자는 전체 가족 구성원과 시스템 전체 데이터를 총괄할 수 있습니다. <br />
각 사용자는 자신만의 인벤토리를 보유하며 독립적으로 관리할 수 있습니다.

## 👤 User Features

- Add, update, and delete personal inventory items <br />
  개인 물품 등록, 수정, 삭제 기능
- View item categories by room (e.g., kitchen, bedroom) <br />
  공간(예: 주방, 침실)별 분류된 물품 확인
- Secure login and account management <br />
  로그인 및 계정 정보 보안 관리
- Responsive layout across desktop and mobile <br />
  데스크탑 및 모바일 대응 반응형 레이아웃

## 🛠 Admin Features

- Manage family member accounts and shared inventory <br />
  가족 구성원 계정 및 공유 인벤토리 관리
- System-wide control of users, categories, and families <br />
  사용자, 공간 카테고리, 가족 단위에 대한 전반적인 관리
- Role-based permissions and secure access <br />
  역할 기반 권한 부여 및 보안 접근 제어

## 🖼 Screenshots

<details>
<summary>Click to view</summary>

![Main Page](/img/main.png)  
![User Inventory Page](/img/user_inventory.png)  
![Admin Dashboard](/img/admin_inventory.png)

</details>

## 🧰 Tech Stack

| Category     | Tech                               |
| ------------ | ---------------------------------- |
| Frontend     | HTML, CSS, Bootstrap               |
| Backend      | Java Servlet, JSP, JavaBeans, JSTL |
| Architecture | MVC Pattern, Filters               |
| Data Access  | JPA, JDBC                          |
| Email        | JavaMail                           |
| Database     | MySQL                              |
| IDE          | NetBeans                           |
