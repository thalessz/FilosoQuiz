from flask import Flask, request, jsonify
import mysql.connector as mysql

# Configuração do banco de dados
db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'filosoquiz'
}

app = Flask(__name__)

def get_db_connection():
    """Estabelece uma conexão com o banco de dados."""
    return mysql.connect(**db_config)

@app.route('/submit_resultado', methods=['POST'])
def submit_resultado():
    data = request.json
    nome = data.get('nome')
    pontuacao = data.get('pontuacao')
    resultado = data.get('resultado')
    try:
        conn = get_db_connection()
        with conn.cursor() as cursor:
            sql = 'INSERT INTO RESULTADOS (NOME, PONTUACAO, RESULTADO) VALUES (%s, %s, %s)'
            values = (nome, pontuacao, resultado)
            cursor.execute(sql, values)
            conn.commit()
            return jsonify({'message': 'Resultados enviados com sucesso!'}), 200
    except:
        return jsonify({'message': 'Erro ao conectar ao banco de dados.'}), 500
    finally:
        if conn:
            conn.close()
            
@app.route('/fetch_resultados', methods = ['GET'])
def fetch_resultados():
    try:
        conn = get_db_connection()
        with conn.cursor() as cursor:
            cursor.execute('SELECT * FROM RESULTADOS')
            rows = cursor.fetchall()
            return jsonify(rows), 200
    except:
        return jsonify({'message': 'Erro ao conectar ao banco de dados.'}), 500
    finally:
        if conn:
            conn.close()

if __name__ == '__main__':
    app.run(debug=True)
