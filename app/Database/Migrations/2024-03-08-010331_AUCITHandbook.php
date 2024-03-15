<?php

namespace App\Database\Migrations;

use CodeIgniter\Database\Migration;

class AUCITHandbook extends Migration
{
    private array $tables = [
        'users' => 'users',
        'accounts' => 'accounts',
    ];

    public function up()
    {
        $this->usersTable();

        $this->forge->reset();

        $this->authIdentitiesTable();
    }

    public function down()
    {
        $this->forge->dropTable($this->tables['users']);
        $this->forge->dropTable($this->tables['accounts']);
    }

    private function authIdentitiesTable()
    {
        $this->forge->addField([
            'id' => [
                'type' => 'INT',
                'constraint' => '12',
                'unsigned' => true,
                'auto_increment' => true
            ],
            'user_id INT(12) ZEROFILL NOT NULL',
            'email' => [
                'type' => 'VARCHAR',
                'constraint' => 128,
            ],
            'password' => [
                'type' => 'VARCHAR',
                'constraint' => 255
            ],
            'created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP()',
            'updated_at TIMESTAMP NULL',
        ]);
        $this->forge->addPrimaryKey('id');
        $this->forge->addUniqueKey('user_id');
        $this->forge->addUniqueKey('email');
        $this->forge->addForeignKey(
            'user_id',
            $this->tables['users'],
            'id',
            'STRICT',
            'CASCADE'
        );
        $this->forge->createTable($this->tables['accounts'], true);
    }

    private function usersTable()
    {
        $this->forge->addField([
            'id INT(12) ZEROFILL NOT NULL AUTO_INCREMENT',
            'family_name' => [
                'type' => 'VARCHAR',
                'constraint' => 255,
            ],
            'given_name' => [
                'type' => 'VARCHAR',
                'constraint' => 255,
            ],
            'display_name' => [
                'type' => 'VARCHAR',
                'constraint' => 255,
            ],
            'active' => [
                'type' => 'TINYINT',
                'constraint' => 1,
                'null' => true
            ],
            'last_active' => [
                'type' => 'TIMESTAMP',
                'null' => true
            ],
            'created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP()',
            'updated_at TIMESTAMP NULL'
        ]);
        $this->forge->addPrimaryKey('id');
        $this->forge->createTable($this->tables['users'], true);
    }
}
