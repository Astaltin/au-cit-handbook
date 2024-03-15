<?php

namespace App\Models;

use CodeIgniter\Database\ConnectionInterface;
use CodeIgniter\Database\Exceptions\DatabaseException;
use CodeIgniter\Model;
use CodeIgniter\Validation\ValidationInterface;

class Auth extends Model
{
    private Account $accountModel;
    private User $userModel;

    # After validation, get the associated data from auth_identities table.
    # Verify if the password matches the hashed password. 
    # If not, do not authenticate the user (return false).
    # Else, set the session with data signifying that the user is authenticated.

    public function __construct(
        ?ConnectionInterface $db = null,
        ?ValidationInterface $validation = null
    ) {
        parent::__construct($validation);

        $this->accountModel = model('Account');
        $this->userModel = model('User');
    }

    /**
     * @throws ReflectionException
     */
    public function authenticate($data)
    {
        $row = $this->accountModel
            ->findAccountByEmail($data['email']);

        if ($row === null) {
            return false;
        }
        if (!password_verify($data['password'], $row['password'])) {
            return false;
        }

        $session = session();
        $session->set('session_token', $session->session_id);
        $session->set('user_token', $row['user_id']);

        $this->userModel->setAsActive();

        return true;
    }

    /**
     * @throws ReflectionException
     */
    public function logout()
    {
        session()->destroy();

        $this->userModel->setAsActive(false);
    }

    /**
     * Creates a new user
     * 
     * @param array<string, string> $data
     * 
     *  @throws DatabaseException   
     */
    public function createUser($data)
    {
        return $this->userModel->create($data);
    }
}
