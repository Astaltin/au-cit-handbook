<?php

namespace App\Models;

use CodeIgniter\Database\ConnectionInterface;
use CodeIgniter\I18n\Time;
use CodeIgniter\Model;
use CodeIgniter\Validation\ValidationInterface;

class User extends Model
{
    protected $table            = 'users';
    protected $primaryKey       = 'id';
    protected $useAutoIncrement = true;
    protected $returnType       = 'array';
    protected $useSoftDeletes   = false;
    protected $protectFields    = true;
    protected $allowedFields    = [
        'given_name',
        'family_name',
        'display_name',
        'active',
        'last_active'
    ];

    protected bool $allowEmptyInserts = false;

    // Dates
    protected $useTimestamps = false;
    protected $dateFormat    = 'datetime';
    protected $createdField  = 'created_at';
    protected $updatedField  = 'updated_at';
    protected $deletedField  = 'deleted_at';

    // Validation
    protected $validationRules      = [];
    protected $validationMessages   = [];
    protected $skipValidation       = false;
    protected $cleanValidationRules = true;

    // Callbacks
    protected $allowCallbacks = false;
    protected $beforeInsert   = [];
    protected $afterInsert    = [];
    protected $beforeUpdate   = [];
    protected $afterUpdate    = [];
    protected $beforeFind     = [];
    protected $afterFind      = [];
    protected $beforeDelete   = [];
    protected $afterDelete    = [];

    private Account $accountModel;

    public function __construct(?ConnectionInterface $db = null, ?ValidationInterface $validation = null)
    {
        parent::__construct($validation);

        $this->accountModel = model('Account');
    }

    public function create($data)
    {
        $data['givenName'] = $this->capitalizeFirstLetter($data['givenName']);
        $data['family_name'] = $this->capitalizeFirstLetter($data['familyName']);
        $data['displayName'] = $data['givenName'] . ' ' . $data['familyName'];

        $db = $this->db;

        $db->transException(true)->transStrict(true)->transStart();

        $this->insert([
            'given_name' => $data['givenName'],
            'family_name' => $data['familyName'],
            'display_name' => $data['displayName']
        ]);

        $this->accountModel
            ->insert([
                'user_id' => $db->insertID(),
                'email' => $data['email'],
                'password' => $data['password'],
            ]);

        $db->transComplete();

        return $db->transStatus();
    }

    /**
     * @throws ReflectionException
     */
    public function setAsActive($bool = true)
    {
        if ($bool) {
            $this->save([
                'id' => session()->get('user_token'),
                'active' => 1,
                'last_active' => Time::now('Asia/Manila')
            ]);
            return;
        }
        
        $this->save([
            'id' => session()->get('user_token'),
            'active' => 0,
        ]);
    }

    private function capitalizeFirstLetter($string)
    {
        return ucfirst(strtolower($string));
    }
}
