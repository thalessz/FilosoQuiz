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
    try:
        return mysql.connect(**db_config)
    except Exception as e:
        print(f"Erro ao conectar ao banco de dados: {e}")
        return None

@app.route('/submit_resultado', methods=['POST'])
def submit_resultado():
    data = request.json
    if not data or 'nome' not in data or 'pontuacao' not in data or 'resultado' not in data:
        return jsonify({'message': 'Dados inválidos.'}), 400

    nome = data['nome']
    pontuacao = data['pontuacao']
    resultado = data['resultado']

    conn = get_db_connection()
    if conn is None:
        return jsonify({'message': 'Erro ao conectar ao banco de dados.'}), 500

    try:
        cursor = conn.cursor()
        sql = 'INSERT INTO RESULTADOS (NOME, PONTUACAO, RESULTADO) VALUES (%s, %s, %s)'
        values = (nome, pontuacao, resultado)
        cursor.execute(sql, values)
        conn.commit()
        return jsonify({'message': 'Resultados enviados com sucesso!'}), 200
    except Exception as e:
        return jsonify({'message': f'Erro ao inserir dados: {e}'}), 500
    finally:
        if conn is not None:
            if 'cursor' in locals() and cursor is not None:
                cursor.close()
            conn.close()

@app.route('/fetch_resultados/<string:nome>', methods = ['GET'])
def fetch_resultados(nome):
    conn = get_db_connection()
    if conn is None:
        return jsonify({'message': 'Erro ao conectar ao banco de dados.'}), 500

    try:
        cursor = conn.cursor()
        # Usar DISTINCT para remover resultados duplicados
        query = """
            SELECT DISTINCT NOME, PONTUACAO, RESULTADO 
            FROM RESULTADOS 
            ORDER BY CASE WHEN NOME = %s THEN 0 ELSE 1 END, PONTUACAO DESC 
            LIMIT 5;
        """
        cursor.execute(query, (nome,))
        rows = cursor.fetchall()

        # Converter os resultados em uma lista de dicionários
        resultados = []
        for row in rows:
            resultado = {
                'nome': row[0],
                'pontuacao': row[1],
                'resultado': row[2]
            }
            resultados.append(resultado)

        return jsonify(resultados), 200
    except Exception as e:
        return jsonify({'message': f'Erro ao fetch dados: {e}'}), 500
    finally:
        if conn is not None:
            if 'cursor' in locals() and cursor is not None:
                cursor.close()
            conn.close()

if __name__ == '__main__':
    app.run(debug=True)
